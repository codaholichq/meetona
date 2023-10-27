import { Form, Field, ErrorMessage } from "vee-validate";

export default {
  install(app) {
    app.component("Form", Form);
    app.component("Field", Field);
    app.component("ErrorMessage", ErrorMessage);
  }
};
