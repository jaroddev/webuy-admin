import { IShop } from 'app/shared/model/shop.model';

export interface IProduct {
  id?: number;
  name?: string;
  price?: number;
  stock?: number;
  logoContentType?: string;
  logo?: any;
  shop?: IShop;
}

export const defaultValue: Readonly<IProduct> = {};
