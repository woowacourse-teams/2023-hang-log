import { END_POINTS } from '@constants/api';
import { useQuery } from '@tanstack/react-query';

import { axiosInstance } from '@api/axiosInstance';

const fetchTrips = async () => {
  return await axiosInstance.get(END_POINTS.TRIPS);
};

export const useGetTrips = () => {
  const { isLoading, isFetching, data, isSuccess, refetch } = useQuery(['trips'], fetchTrips);

  return { isLoading, isFetching, data, isSuccess, refetch };
};
