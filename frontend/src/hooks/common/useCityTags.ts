import { City } from '@/components/common/CitySearchBar/CitySearchBar';
import { CITY_TAG_MAX_LENGTH } from '@constants/ui';
import { useState } from 'react';

export const useCityTags = (initialCityTags: City[]) => {
  const [cityTags, setCityTags] = useState(initialCityTags);

  const getCityName = (city: string) => {
    return city.split(',')[0];
  };

  const addCityTag = (selectedCity: City) => {
    const cityName = getCityName(selectedCity.name);

    setCityTags((prevCityTags) => {
      const hasCityTag = prevCityTags.map((tags) => tags.id).includes(selectedCity.id);

      if (hasCityTag) {
        const filteredCityTags = prevCityTags.filter((cityTag) => cityTag.id !== selectedCity.id);
        return [...filteredCityTags, { id: selectedCity.id, name: cityName }];
      }

      if (prevCityTags.length >= CITY_TAG_MAX_LENGTH) {
        return prevCityTags;
      }

      return [...prevCityTags, { id: selectedCity.id, name: cityName }];
    });
  };

  const deleteCityTag = (selectedCity: City) => {
    setCityTags((prevCityTags) => prevCityTags.filter((cityTag) => cityTag.id !== selectedCity.id));
  };

  return { cityTags, addCityTag, deleteCityTag };
};
