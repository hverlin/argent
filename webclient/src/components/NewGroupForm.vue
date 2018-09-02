<template>
  <section>
    <h1 class="subtitle">Create a new group</h1>
    <form v-on:submit.prevent="onSubmit">
      <b-field label="Group name">
        <b-input v-model="groupName" placeholder="my group name" maxlength="255" required></b-input>
      </b-field>

      <b-field label="Description">
        <b-input type="textarea" maxlength="500" placeholder="optional description..."></b-input>
      </b-field>

      <b-field>
        <p class="control">
          <button class="button is-success">
            Create group
          </button>
        </p>
      </b-field>
    </form>
  </section>
</template>

<script lang="ts">
  import { Component, Vue } from 'vue-property-decorator';
  import { Getter } from '../common/decorators';
  import { CREATE_GROUP } from '../store/actions.type';
  import { Action } from 'vuex-class';


  @Component
  export default class NewGroupForm extends Vue {
    protected groupName = '';
    @Getter('currentGroup') private currentGroup: any;
    @Action(CREATE_GROUP) private createGroup: any;

    public onSubmit() {
      this.createGroup({ name: this.groupName, currency: 'EUR' })
          .then(() => this.$router.push({ name: 'group', params: { id: this.groupName } }));
    }
  }
</script>

<style scoped lang="scss">
</style>
