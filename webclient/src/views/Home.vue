<template>
  <div>
    <WelcomeMessage/>
  </div>
</template>

<script lang="ts">
  import {Component, Vue} from 'vue-property-decorator';
  import NewGroupForm from '@/components/NewGroupForm.vue';
  import WelcomeMessage from '@/components/WelcomeMessage.vue';

  @Component({
    components: {
      WelcomeMessage,
      NewGroupForm,
    },
  })
  export default class Home extends Vue {

  }
</script>
