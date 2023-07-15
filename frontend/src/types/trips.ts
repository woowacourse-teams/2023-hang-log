export interface TripsType {
  id: number;
  title: string;
  imageUrls: string;
  cities: Cities[];
  startDate: string;
  endDate: string;
  description: string;
}

export interface Cities {
  id: number;
  name: string;
}
