import './initComponents.js';

// getting language of the PLF 
const lang = typeof eXo !== 'undefined' ? eXo.env.portal.language : 'en';

// should expose the locale ressources as REST API 
const url = `${Vue.prototype.$spacesConstants.PORTAL}/${Vue.prototype.$spacesConstants.PORTAL_REST}/i18n/bundle/locale.portlet.social.ExternalSpacesListApplication-${lang}.json`;


// get overrided components if exists
if (extensionRegistry) {
  const components = extensionRegistry.loadComponents('ExternalSpacesList');
  if (components && components.length > 0) {
    components.forEach(cmp => {
      Vue.component(cmp.componentName, cmp.componentOptions);
    });
  }
}

document.dispatchEvent(new CustomEvent('displayTopBarLoading'));

Vue.use(Vuetify);
const vuetify = new Vuetify(eXo.env.portal.vuetifyPreset);

const appId = 'ExternalSpacesListPortlet';

export function init() {
  //getting locale ressources
  exoi18n.loadLanguageAsync(lang, url)
    .then(i18n => {
      const appElement = document.createElement('div');
      appElement.id = appId;

      // init Vue app when locale ressources are ready
      Vue.createApp({
        mounted() {
          document.dispatchEvent(new CustomEvent('hideTopBarLoading'));
        },
        template: `<external-spaces-list id="${appId}" v-cacheable />`,
        i18n,
        vuetify,
      }, appElement, 'External Spaces List');
    });
}
