<template>
  <form v-on:submit.prevent="addMember">
    <div class="modal-card" style="width: auto">
      <header class="modal-card-head">
        <p class="modal-card-title">Add new member</p>
      </header>
      <section class="modal-card-body">
        <b-field label="amount">
          <b-input
                  v-model="memberName"
                  type="text"
                  placeholder="member name"
                  autofocus
                  required>
          </b-input>
        </b-field>
      </section>
      <footer class="modal-card-foot">
        <button class="button" type="button" @click="$parent.close()">Close</button>
        <button class="button is-primary" type="submit">Add member</button>
      </footer>
    </div>
  </form>
</template>

<script lang="ts">
  import { Component, Vue } from 'vue-property-decorator';
  import { Action, State } from 'vuex-class';
  import { ADD_MEMBER } from '../store/actions.type';

  @Component
  export default class AddMemberModal extends Vue {
    @State('currentGroup') public currentGroup: any;
    @Action(ADD_MEMBER) private addMemberAction: any;

    private memberName = '';

    private addMember() {
      this.addMemberAction({
        groupId: this.currentGroup.id,
        name: this.memberName,
      }).then(() => {
        return (this.$parent as any).close();
      });
    }
  }
</script>

<style scoped>

</style>
