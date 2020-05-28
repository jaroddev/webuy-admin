import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IShopGroup, defaultValue } from 'app/shared/model/shop-group.model';

export const ACTION_TYPES = {
  FETCH_SHOPGROUP_LIST: 'shopGroup/FETCH_SHOPGROUP_LIST',
  FETCH_SHOPGROUP: 'shopGroup/FETCH_SHOPGROUP',
  CREATE_SHOPGROUP: 'shopGroup/CREATE_SHOPGROUP',
  UPDATE_SHOPGROUP: 'shopGroup/UPDATE_SHOPGROUP',
  DELETE_SHOPGROUP: 'shopGroup/DELETE_SHOPGROUP',
  SET_BLOB: 'shopGroup/SET_BLOB',
  RESET: 'shopGroup/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IShopGroup>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ShopGroupState = Readonly<typeof initialState>;

// Reducer

export default (state: ShopGroupState = initialState, action): ShopGroupState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SHOPGROUP_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SHOPGROUP):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SHOPGROUP):
    case REQUEST(ACTION_TYPES.UPDATE_SHOPGROUP):
    case REQUEST(ACTION_TYPES.DELETE_SHOPGROUP):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SHOPGROUP_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SHOPGROUP):
    case FAILURE(ACTION_TYPES.CREATE_SHOPGROUP):
    case FAILURE(ACTION_TYPES.UPDATE_SHOPGROUP):
    case FAILURE(ACTION_TYPES.DELETE_SHOPGROUP):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SHOPGROUP_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SHOPGROUP):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SHOPGROUP):
    case SUCCESS(ACTION_TYPES.UPDATE_SHOPGROUP):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SHOPGROUP):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.SET_BLOB: {
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType,
        },
      };
    }
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/shop-groups';

// Actions

export const getEntities: ICrudGetAllAction<IShopGroup> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SHOPGROUP_LIST,
  payload: axios.get<IShopGroup>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IShopGroup> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SHOPGROUP,
    payload: axios.get<IShopGroup>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IShopGroup> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SHOPGROUP,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IShopGroup> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SHOPGROUP,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IShopGroup> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SHOPGROUP,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType,
  },
});

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
