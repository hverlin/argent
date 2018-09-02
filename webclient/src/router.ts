import Vue from 'vue';
import Router from 'vue-router';
import Home from './views/Home.vue';
import Group from './views/Group.vue';
import CreateGroup from './views/CreateGroup.vue';
import MyGroups from './views/MyGroups.vue';

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
    },
    {
      path: '/group/:id',
      name: 'group',
      component: Group,
    },
    {
      path: '/create-group',
      name: 'CreateGroup',
      component: CreateGroup,
    },
    {
      path: '/my-groups',
      name: 'myGroups',
      component: MyGroups,
    },
  ],
});
