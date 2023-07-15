import { CITY } from '@constants/city';
import { useState } from 'react';

export const useCityTags = (initialCityTags: string[]) => {
  const [cityTags, setCityTags] = useState<string[]>(initialCityTags);

  const getCityName = (city: string) => {
    return city.split(',')[0];
  };

  const addCityTag = (selectedCity: string) => {
    const city = getCityName(selectedCity);

    setCityTags((cityTags) => {
      if (cityTags.includes(city)) {
        const filteredCityTags = cityTags.filter((cityTag) => cityTag !== city);
        return [...filteredCityTags, city];
      }

      if (cityTags.length >= CITY.MAX_NUM) {
        return cityTags;
      }

      return [...cityTags, city];
    });
  };

  const deleteCityTag = (selectedCity: string) => {
    const city = getCityName(selectedCity);

    setCityTags((cityTags) => cityTags.filter((cityTag) => cityTag !== city));
  };

  return { cityTags, addCityTag, deleteCityTag };
};
