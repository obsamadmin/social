<template>
  <v-app>
    <v-menu
      rounded="rounded"
      elevation="2"
      v-model="menu"
      open-on-hover
      :close-on-content-click="false"
      :nudge-width="200"
      max-width="350"
      min-width="300"
      offset-y>
      <template #activator="{ on, attrs }">
        <div
          v-on="on"
          v-bind="attrs"
          class="d-inline-flex">
          <a
            v-if="logoPath"
            id="UserHomePortalLink"
            :href="portalPath"
            class="pe-3 logoContainer">
            <img
              :src="logoPath"
              class="spaceAvatar"
              :alt="logoTitle">
          </a>
          <a
            :href="portalPath"
            :class="'ps-2 align-self-center brandingContainer space'">
            <div class="logoTitle subtitle-2 font-weight-bold text-truncate">
              {{ logoTitle }}
            </div>
          </a>
        </div>
      </template>
      <v-card elevation="2">
        <v-list class="pa-0">
          <v-list-item>
            <v-list-item-avatar
              width="60"
              height="60">
              <v-img
                class="object-fit-cover"
                :src="logoPath" />
            </v-list-item-avatar>
            <v-list-item-content class="pb-0">
              <v-list-item-title>
                <v-tooltip bottom>
                  <template #activator="{ on, attrs }">
                    <span
                      v-on="on"
                      v-bind="attrs"
                      class="blue--text text--darken-3 font-weight-bold">
                      {{ logoTitle }}
                    </span>
                  </template>
                  <span>{{ logoTitle }}</span>
                </v-tooltip>
              </v-list-item-title>
              <v-list-item-subtitle>
                {{ membersNumber }} {{ $t('space.logo.banner.popover.members') }}
              </v-list-item-subtitle>
              <p class="text-truncate-3 text-caption text--primary font-weight-medium">
                {{ spaceDescription }}
              </p>
            </v-list-item-content>
          </v-list-item>
        </v-list>
        <v-divider />
        <v-list class="pa-0 mt-0 mb-0">
          <v-list-item class="pt-0 pb-0">
            <v-list-item-content>
              <v-container class="pa-0">
                <v-row no-gutters>
                  <v-col
                    cols="6"
                    class="pt-1 body-2 grey--text text-truncate text--darken-1"
                    justify="center">
                    {{ $t('space.logo.banner.popover.managers') }}
                  </v-col>
                  <v-col
                    cols="6"
                    justify="center"
                    class="d-flex flex-nowrap pa-0">
                    <exo-user-avatars-list
                      :users="mangersToDisplay"
                      :icon-size="30"
                      :popover="false"
                      max="4"
                      retrieve-extra-information
                      avatar-overlay-position />
                  </v-col>
                </v-row>
              </v-container>
            </v-list-item-content>
          </v-list-item>
        </v-list>
        <v-divider />
        <v-list
          class="pa-0 mt-0 mb-0">
          <v-list-item
            class="pt-0 pb-0">
            <v-list-item-content>
              <v-list-item-title>
                <v-btn
                  :href="homePath"
                  color="primary"
                  text
                  class="pa-0 pe-2">
                  <v-icon
                    dense
                    right
                    class="me-1">
                    mdi-home
                  </v-icon>
                  <span class="text-caption pt-1">{{ $t('space.logo.banner.popover.home') }}</span>
                </v-btn>
              </v-list-item-title>
            </v-list-item-content>
            <v-list-item-action class="space-logo-popover flex-row">
              <extension-registry-components
                :params="params"
                name="SpacePopover"
                type="space-popover-action"
                parent-element="div"
                element="div"
                element-class="mx-auto ma-lg-0" />
              <space-popover-action-component />
            </v-list-item-action>
          </v-list-item>
        </v-list>
      </v-card>
    </v-menu>
  </v-app>
</template>

<script>
export default {
  props: {
    logoPath: {
      type: String,
      default: function () {
        return null;
      },
    },
    portalPath: {
      type: String,
      default: function () {
        return null;
      },
    },
    logoTitle: {
      type: String,
      default: function () {
        return null;
      },
    },
    membersNumber: {
      type: Number,
      default: function () {
        return null;
      },
    },
    spaceDescription: {
      type: String,
      default: function () {
        return '';
      },
    },
    homePath: {
      type: String,
      default: function () {
        return '';
      },
    },
    managers: {
      type: Array,
      default: function () {
        return null;
      },
    },
  },
  computed: {
    KeepDisplayManagers() {
      return this.managers && this.managers.length <= this.sizeToDisplay;
    },
    mangersToDisplay() {
      return this.managers;
    },
    params() {
      return {
        identityType: 'space',
        identityId: eXo.env.portal.spaceId
      };
    },
  },
};
</script>