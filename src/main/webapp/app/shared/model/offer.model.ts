import { Moment } from 'moment';
import { IProduct } from 'app/shared/model/product.model';
import { IShop } from 'app/shared/model/shop.model';

export interface IOffer {
  id?: number;
  start?: string;
  end?: string;
  discountPrice?: number;
  discountQuantity?: number;
  product?: IProduct;
  shop?: IShop;
}

export const defaultValue: Readonly<IOffer> = {};
