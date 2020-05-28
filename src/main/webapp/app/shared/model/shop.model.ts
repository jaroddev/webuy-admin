import { IOffer } from 'app/shared/model/offer.model';
import { IProduct } from 'app/shared/model/product.model';
import { IShopGroup } from 'app/shared/model/shop-group.model';
import { IAddress } from 'app/shared/model/address.model';

export interface IShop {
  id?: number;
  offers?: IOffer[];
  products?: IProduct[];
  shopGroup?: IShopGroup;
  address?: IAddress;
}

export const defaultValue: Readonly<IShop> = {};
