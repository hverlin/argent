<template>
  <div>
    <TopSection title="Create a group"/>
    <section class="section">
      <div class="container">
        <NewGroupForm></NewGroupForm>
      </div>
    </section>
  </div>
</template>

<script lang="ts">
  import {Component, Vue} from 'vue-property-decorator';
  import NewGroupForm from '@/components/NewGroupForm.vue';
  import TopSection from '@/components/TopSection.vue';

  @Component({
    components: {
      TopSection,
      NewGroupForm,
    },
  })
  export default class CreateGroup extends Vue {

  }
</script>
