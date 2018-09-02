<template>
  <form action="">
    <div class="modal-card" style="width: auto">
      <header class="modal-card-head">
        <p class="modal-card-title">Add new expense</p>
      </header>
      <section class="modal-card-body">
        <b-field label="amount">
          <b-input
                  type="number"
                  value="200"
                  step="0.01"
                  placeholder="Amount of the expense"
                  autofocus
                  required>
          </b-input>
        </b-field>

        <b-field label="description">
          <b-input
                  type="text"
                  value="bla bla bla"
                  placeholder="Description of the expense"
                  required>
          </b-input>
        </b-field>

      </section>
      <footer class="modal-card-foot">
        <button class="button" type="button" @click="$parent.close()">Close</button>
        <button class="button is-primary">Add expense</button>
      </footer>
    </div>
  </form>
</template>

<script lang="ts">
  import { Component, Vue } from 'vue-property-decorator';

  @Component
  export default class AddExpenseModal extends Vue {
  }
</script>

<style scoped>

</style>
