export class ThemeLoader {
	/**
	 * Load a theme by its slug
	 * @param {string} themeSlug - The slug of the theme to load
	 * @returns {Promise<any>} The loaded theme module
	 */
	async loadTheme(themeSlug) {
		try {
			// Dynamically import the theme
			const theme = await import(`../../../themes/${themeSlug}/index.js`);
			return theme.default || theme;
		} catch (error) {
			console.error(`Failed to load theme: ${themeSlug}`, error);
			throw new Error(`Theme '${themeSlug}' not found`);
		}
	}

	/**
	 * Load theme configuration
	 * @param {string} themeSlug - The slug of the theme
	 * @returns {Promise<object>} The theme configuration object
	 */
	async loadThemeConfig(themeSlug) {
		try {
			const response = await fetch(`/themes/${themeSlug}/theme.json`);
			if (!response.ok) {
				throw new Error('Theme config not found');
			}
			return await response.json();
		} catch (error) {
			console.error(`Failed to load theme config: ${themeSlug}`, error);
			throw error;
		}
	}

	/**
	 * Load theme styles
	 * @param {string} themeSlug - The slug of the theme
	 */
	loadThemeStyles(themeSlug) {
		const link = document.createElement('link');
		link.rel = 'stylesheet';
		link.href = `/themes/${themeSlug}/assets/css/theme.css`;
		link.id = `theme-styles-${themeSlug}`;
		document.head.appendChild(link);
	}

	/**
	 * Remove theme styles
	 * @param {string} themeSlug - The slug of the theme
	 */
	removeThemeStyles(themeSlug) {
		const link = document.getElementById(`theme-styles-${themeSlug}`);
		if (link) {
			link.remove();
		}
	}
}

export default new ThemeLoader();
