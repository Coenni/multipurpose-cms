export class PluginLoader {
	/**
	 * Load a widget component by plugin slug and widget name
	 * @param {string} pluginSlug - The slug of the plugin
	 * @param {string} widgetName - The name of the widget (optional, defaults to plugin slug)
	 * @returns {Promise<any>} The loaded widget component
	 */
	async loadWidget(pluginSlug, widgetName = null) {
		const name = widgetName || pluginSlug;
		try {
			// Try to load from core-widgets first
			const widget = await import(`../../../plugins/core-widgets/${name}/Widget.svelte`);
			return widget.default;
		} catch (error) {
			console.error(`Failed to load widget: ${pluginSlug}/${name}`, error);
			throw new Error(`Widget '${name}' not found in plugin '${pluginSlug}'`);
		}
	}

	/**
	 * Load widget configuration
	 * @param {string} pluginSlug - The slug of the plugin
	 * @param {string} widgetName - The name of the widget
	 * @returns {Promise<object>} The widget configuration object
	 */
	async loadWidgetConfig(pluginSlug, widgetName = null) {
		const name = widgetName || pluginSlug;
		try {
			const response = await fetch(`/plugins/core-widgets/${name}/widget.json`);
			if (!response.ok) {
				throw new Error('Widget config not found');
			}
			return await response.json();
		} catch (error) {
			console.error(`Failed to load widget config: ${name}`, error);
			throw error;
		}
	}

	/**
	 * Load widget styles
	 * @param {string} pluginSlug - The slug of the plugin
	 * @param {string} widgetName - The name of the widget
	 */
	loadWidgetStyles(pluginSlug, widgetName = null) {
		const name = widgetName || pluginSlug;
		const link = document.createElement('link');
		link.rel = 'stylesheet';
		link.href = `/plugins/core-widgets/${name}/widget.css`;
		link.id = `widget-styles-${name}`;
		document.head.appendChild(link);
	}

	/**
	 * Remove widget styles
	 * @param {string} widgetName - The name of the widget
	 */
	removeWidgetStyles(widgetName) {
		const link = document.getElementById(`widget-styles-${widgetName}`);
		if (link) {
			link.remove();
		}
	}
}

export default new PluginLoader();
