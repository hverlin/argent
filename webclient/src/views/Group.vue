<template>
  <div class="group">
    <b-loading :is-full-page="true" :active.sync="isLoading"></b-loading>
    <template v-if="!showError">
      <section class="hero is-info">
        <div class="hero-body">
          <div class="container">
            <h1 class="title">
              {{ currentGroup.name }}
            </h1>
            <h2 class="subtitle">
              You can see the expenses (in {{ currentGroup.currency }}) of your group below
            </h2>
          </div>
        </div>
      </section>
      <section class="section">
        <div class="container">
          <nav class="level">
            <div class="level-left">
              <div class="level-item">
                <h2 class="subtitle">Expenses</h2>
              </div>
            </div>

            <div class="level-right">
              <p class="level-item">
                <button class="button" @click="openAddExpenseModal()">Add expense</button>
              </p>
            </div>
          </nav>
          <b-table :data="currentGroup.expenses" :columns="expensesColumns">
            <template slot="empty">
              <section class="section">
                <div class="content has-text-grey has-text-centered">
                  <p>No expenses yet</p>
                </div>
              </section>
            </template>
          </b-table>
        </div>
      </section>
      <section class="section">
        <div class="container">
          <nav class="level">
            <div class="level-left">
              <div class="level-item">
                <h2 class="subtitle">Group members</h2>
              </div>
            </div>

            <div class="level-right">
              <p class="level-item">
                <button class="button" @click="openAddMemberModal()">Add member</button>
              </p>
            </div>
          </nav>
          <b-table :data="currentGroup.members" :columns="memberColumns">
            <template slot="empty">
              <section class="section">
                <div class="content has-text-grey has-text-centered">
                  <p>No users in this group</p>
                </div>
              </section>
            </template>
          </b-table>
        </div>
      </section>
    </template>
    <template v-else>
      <section class="section">
        No group with the name {{ $route.params.id }}
      </section>
    </template>
  </div>
</template>
<script lang="ts">
  import { Component, Vue } from 'vue-property-decorator';
  import { FETCH_GROUP } from '../store/actions.type';
  import { Action, State } from 'vuex-class';
  import AddExpenseModal from '@/components/AddExpenseModal.vue';
  import AddMemberModal from '@/components/AddMemberModal.vue';

  @Component({
    components: { AddExpenseModal },
  })
  export default class Group extends Vue {
    @State('currentGroup') public currentGroup: any;
    protected showError = false;
    protected isLoading = true;

    protected expensesColumns = [{
      field: 'description',
      label: 'description',
    }, {
      field: 'amount',
      label: 'amount',
    }, {
      field: 'createdAt',
      label: 'created At',
    }];

    protected memberColumns = [{
      field: 'name',
      label: 'name',
    }];


    @Action(FETCH_GROUP) private fetchGroup: any;

    private mounted() {
      this.fetchGroup(this.$route.params.id)
          .catch(() => this.showError = true)
          .finally(() => this.isLoading = false);
    }

    private openAddExpenseModal() {
      this.$modal.open({
        parent: this,
        component: AddExpenseModal,
      });
    }

    private openAddMemberModal() {
      this.$modal.open({
        parent: this,
        component: AddMemberModal,
      });
    }
  }
</script>

<style scoped lang="scss">
</style>
