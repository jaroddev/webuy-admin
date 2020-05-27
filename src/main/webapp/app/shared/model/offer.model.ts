import { IProduct } from 'app/shared/model/product.model';

export interface IOffer {
  id?: number;
  product?: IProduct;
}

export const defaultValue: Readonly<IOffer> = {};
