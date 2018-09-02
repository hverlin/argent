import Vue from 'vue';
import Buefy from 'buefy';
import 'buefy/lib/buefy.css';

Vue.use(Buefy);

import App from './App.vue';
import router from './router';
import store from './store/store';
import './registerServiceWorker';
import ApiService from '@/common/api.service';

Vue.config.productionTip = false;

ApiService.init();

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount('#app');
