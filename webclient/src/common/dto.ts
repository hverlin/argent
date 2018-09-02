export interface NewGroup {
  name: string;
  currency: string;
}

export interface NewUser {
  name: string;
}

export interface AddMemberPayload {
  name: string;
  groupId: string;
}
