import { useState } from 'react';

import type { CityData } from '@type/city';

import { CITY_TAG_MAX_LENGTH } from '@constants/ui';

export const useCityTags = (initialCityTags: CityData[]) => {
  const [cityTags, setCityTags] = useState(initialCityTags);

  const getCityName = (city: string) => {
    return city.split(',')[0];
  };

  const addCityTag = (selectedCity: CityData) => {
    const cityName = getCityName(selectedCity.name);

    setCityTags((prevCityTags) => {
      const hasCityTag = !!prevCityTags.filter((tags) => tags.id === selectedCity.id).length;

      if (hasCityTag) {
        const filteredCityTags = prevCityTags.filter((cityTag) => cityTag.id !== selectedCity.id);
        return [...filteredCityTags, { ...selectedCity, name: cityName }];
      }

      if (prevCityTags.length >= CITY_TAG_MAX_LENGTH) {
        return prevCityTags;
      }

      return [...prevCityTags, { ...selectedCity, name: cityName }];
    });
  };

  const deleteCityTag = (selectedCity: CityData) => {
    setCityTags((prevCityTags) => prevCityTags.filter((cityTag) => cityTag.id !== selectedCity.id));
  };

  return { cityTags, addCityTag, deleteCityTag };
};
