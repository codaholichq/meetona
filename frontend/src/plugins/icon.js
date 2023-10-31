import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import {
  faHome,
  faUser,
  faEdit,
  faUserPlus,
  faTrashAlt,
  faSignInAlt,
  faSignOutAlt,
  faMagnifyingGlass
} from "@fortawesome/free-solid-svg-icons";

library.add(
  faHome,
  faUser,
  faEdit,
  faUserPlus,
  faTrashAlt,
  faSignInAlt,
  faSignOutAlt,
  faMagnifyingGlass
);

export default {
  install(app) {
    app.component("Icon", FontAwesomeIcon);
  }
};
