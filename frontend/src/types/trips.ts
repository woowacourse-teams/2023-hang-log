export interface TripsType {
  id: number;
  title: string;
  imageUrl: string;
  cities: Cities[];
  startDate: string;
  endDate: string;
  description?: string | null;
}

export interface Cities {
  id: number;
  name: string;
}
