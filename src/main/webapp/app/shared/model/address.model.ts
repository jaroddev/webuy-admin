export interface IAddress {
  id?: number;
  city?: string;
  postalCode?: string;
  department?: string;
}

export const defaultValue: Readonly<IAddress> = {};
