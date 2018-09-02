<template>
  <form v-on:submit.prevent="onSubmit">
    <b-field label="Enter a group name">
    </b-field>
    <b-field grouped>
      <b-input autofocus v-model="groupName" placeholder="Search..." maxlength="255" expanded></b-input>
      <p class="control">
        <button class="button is-primary">Search</button>
      </p>
    </b-field>
  </form>
</template>
<script lang="ts">
  import { Component, Vue } from 'vue-property-decorator';

  @Component
  export default class SearchGroup extends Vue {
    protected groupName = '';

    public onSubmit() {
      this.$router.push({ name: 'group', params: { id: this.groupName } });
    }
  }
</script>
