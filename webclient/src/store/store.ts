import Vue from 'vue';
import Vuex from 'vuex';
import { ADD_MEMBER, CREATE_GROUP, CREATE_USER, FETCH_GROUP } from '@/store/actions.type';
import { GroupService, UserService } from '@/common/api.service';
import { AddMemberPayload, NewGroup, NewUser } from '@/common/dto';
import { SET_CURRENT_GROUP } from '@/store/mutations.type';
import createPersistedState from 'vuex-persistedstate';

Vue.use(Vuex);

const initialState = {
  currentUser: {},
  currentGroup: {},
  myGroups: {},
};

// @ts-ignore
export default new Vuex.Store({
  state: Object.assign({}, initialState),
  mutations: {
    [SET_CURRENT_GROUP](state, group) {
      state.currentGroup = group;
      // @ts-ignore
      state.myGroups[group.id] = group;
    },
  },
  actions: {
    async [CREATE_GROUP]({ commit }, group: NewGroup): Promise<any> {
      const { data } = await GroupService.create(group);
      return data;
    },
    async [CREATE_USER]({ commit }, user: NewUser): Promise<any> {
      const { data } = await UserService.create(user);
      return data;
    },
    async [FETCH_GROUP]({ commit }, groupId: string): Promise<any> {
      const { data } = await GroupService.fetchByName(groupId);
      commit(SET_CURRENT_GROUP, data);
      return data;
    },
    async [ADD_MEMBER]({ commit }, addMemberPayload: AddMemberPayload): Promise<any> {
      await GroupService.addMember(addMemberPayload);
      const { data } = await GroupService.fetchById(addMemberPayload.groupId);
      commit(SET_CURRENT_GROUP, data);
      return data;
    },
  },
  plugins: [createPersistedState()],
});
