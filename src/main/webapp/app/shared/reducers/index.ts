import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import address, {
  AddressState
} from 'app/entities/address/address.reducer';
// prettier-ignore
import shop, {
  ShopState
} from 'app/entities/shop/shop.reducer';
// prettier-ignore
import product, {
  ProductState
} from 'app/entities/product/product.reducer';
// prettier-ignore
import offer, {
  OfferState
} from 'app/entities/offer/offer.reducer';
// prettier-ignore
import shopGroup, {
  ShopGroupState
} from 'app/entities/shop-group/shop-group.reducer';
// prettier-ignore
import group, {
  GroupState
} from 'app/entities/group/group.reducer';
// prettier-ignore
import message, {
  MessageState
} from 'app/entities/message/message.reducer';
// prettier-ignore
import groupInvitation, {
  GroupInvitationState
} from 'app/entities/group-invitation/group-invitation.reducer';
// prettier-ignore
import friendRequest, {
  FriendRequestState
} from 'app/entities/friend-request/friend-request.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly address: AddressState;
  readonly shop: ShopState;
  readonly product: ProductState;
  readonly offer: OfferState;
  readonly shopGroup: ShopGroupState;
  readonly group: GroupState;
  readonly message: MessageState;
  readonly groupInvitation: GroupInvitationState;
  readonly friendRequest: FriendRequestState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  address,
  shop,
  product,
  offer,
  shopGroup,
  group,
  message,
  groupInvitation,
  friendRequest,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
});

export default rootReducer;