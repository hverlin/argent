<template>
  <div class="group">
    <TopSection title="My groups" subtitle="The groups you accessed are shown here"></TopSection>
    <section class="section">
      <div class="container">
        <b-table :data="Object.values(myGroups)" v-if=Object.values(myGroups).length> 0"">
          <template slot-scope="props">
            <b-table-column field="name" label="Name">
              <router-link :to="'group/' + props.row.name">{{props.row.name}}</router-link>
            </b-table-column>
          </template>
        </b-table>
        <div v-else>
          The group you have accessed will be shown here.
        </div>
        <hr>
        <SearchGroup />
      </div>
    </section>
  </div>
</template>
<script lang="ts">
  import { Component, Vue } from 'vue-property-decorator';
  import { State } from 'vuex-class';
  import TopSection from '@/components/TopSection.vue';
  import SearchGroup from '@/components/SearchGroup.vue';

  @Component({
    components: { SearchGroup, TopSection },
  })
  export default class MyGroups extends Vue {
    @State('myGroups') public myGroups: any;
  }
</script>

<style scoped lang="scss">
</style>
