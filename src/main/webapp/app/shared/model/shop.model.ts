import { IAddress } from 'app/shared/model/address.model';
import { IProduct } from 'app/shared/model/product.model';
import { IOffer } from 'app/shared/model/offer.model';

export interface IShop {
  id?: number;
  address?: IAddress;
  products?: IProduct[];
  offers?: IOffer[];
}

export const defaultValue: Readonly<IShop> = {};
