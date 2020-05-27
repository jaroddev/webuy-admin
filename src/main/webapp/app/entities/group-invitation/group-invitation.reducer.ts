import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IGroupInvitation, defaultValue } from 'app/shared/model/group-invitation.model';

export const ACTION_TYPES = {
  FETCH_GROUPINVITATION_LIST: 'groupInvitation/FETCH_GROUPINVITATION_LIST',
  FETCH_GROUPINVITATION: 'groupInvitation/FETCH_GROUPINVITATION',
  CREATE_GROUPINVITATION: 'groupInvitation/CREATE_GROUPINVITATION',
  UPDATE_GROUPINVITATION: 'groupInvitation/UPDATE_GROUPINVITATION',
  DELETE_GROUPINVITATION: 'groupInvitation/DELETE_GROUPINVITATION',
  RESET: 'groupInvitation/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IGroupInvitation>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type GroupInvitationState = Readonly<typeof initialState>;

// Reducer

export default (state: GroupInvitationState = initialState, action): GroupInvitationState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_GROUPINVITATION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_GROUPINVITATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_GROUPINVITATION):
    case REQUEST(ACTION_TYPES.UPDATE_GROUPINVITATION):
    case REQUEST(ACTION_TYPES.DELETE_GROUPINVITATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_GROUPINVITATION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_GROUPINVITATION):
    case FAILURE(ACTION_TYPES.CREATE_GROUPINVITATION):
    case FAILURE(ACTION_TYPES.UPDATE_GROUPINVITATION):
    case FAILURE(ACTION_TYPES.DELETE_GROUPINVITATION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_GROUPINVITATION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_GROUPINVITATION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_GROUPINVITATION):
    case SUCCESS(ACTION_TYPES.UPDATE_GROUPINVITATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_GROUPINVITATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/group-invitations';

// Actions

export const getEntities: ICrudGetAllAction<IGroupInvitation> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_GROUPINVITATION_LIST,
  payload: axios.get<IGroupInvitation>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IGroupInvitation> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_GROUPINVITATION,
    payload: axios.get<IGroupInvitation>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IGroupInvitation> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_GROUPINVITATION,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IGroupInvitation> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_GROUPINVITATION,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IGroupInvitation> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_GROUPINVITATION,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
