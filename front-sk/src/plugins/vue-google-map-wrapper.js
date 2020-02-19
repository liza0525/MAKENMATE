import googleMapFactory from '@/components/js/map'
import googleMapMarker from '@/components/js/marker'
import googleMapCustomControl from '@/components/js/custom-control'

function install (Vue, options) {
  const { apiKey } = options

  const googleMap = googleMapFactory(apiKey)

  const components = {
    googleMap,
    googleMapMarker,
    googleMapCustomControl
  }

  Object.entries(components).forEach(([componentName, component]) => {
    Vue.component(componentName, component)
  })
}

export default install