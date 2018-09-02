import axios, { AxiosRequestConfig } from 'axios';
import JwtService from '@/common/jwt.service';
import { API_URL } from '@/common/config';
import { AddMemberPayload, NewGroup, NewUser } from '@/common/dto';

const ApiService = {
  init() {
    axios.defaults.baseURL = API_URL;
  },

  setHeader() {
    axios.defaults.headers.common.Authorization = `Token ${JwtService.getToken()}`;
  },

  query(resource: string, params: AxiosRequestConfig) {
    return axios
        .get(resource, params)
        .catch((error) => {
          throw new Error(`[RWV] ApiService ${error}`);
        });
  },

  get(resource: string, slug = '') {
    return axios
        .get(`${resource}/${slug}`)
        .catch((error) => {
          throw new Error(`[RWV] ApiService ${error}`);
        });
  },

  post(resource: string, data: any) {
    return axios.post(`${resource}`, data);
  },

  update(resource: string, slug: string, params: AxiosRequestConfig) {
    return axios.put(`${resource}/${slug}`, params);
  },

  put(resource: string, data: any) {
    return axios
        .put(`${resource}`, data);
  },

  delete(resource: string) {
    return axios
        .delete(resource)
        .catch((error) => {
          throw new Error(`[RWV] ApiService ${error}`);
        });
  },
};

export default ApiService;

export const GroupService = {
  create(group: NewGroup) {
    return ApiService.post('groups', {
      name: group.name,
      currency: group.currency,
    });
  },
  addMember(addMemberPayload: AddMemberPayload) {
    return ApiService.post(`groups/${addMemberPayload.groupId}/addMember`, {
      name: addMemberPayload.name,
    });
  },
  fetchByName(groupId: string) {
    return ApiService.get('groups', groupId);
  },
  fetchById(groupId: string) {
    return ApiService.get('groups/id', groupId);
  },
};

export const UserService = {
  create(user: NewUser) {
    return ApiService.post('users', {
      name: user.name,
    });
  },
};
