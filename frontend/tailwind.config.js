/** @type {import('tailwindcss').Config} */
export default {
	content: ['./src/**/*.{html,js,svelte,ts}'],
	theme: {
		extend: {
			colors: {
				primary: {
					DEFAULT: '#3B82F6',
					50: '#EBF2FE',
					100: '#D7E6FD',
					200: '#B0CDFB',
					300: '#89B4F9',
					400: '#629BF7',
					500: '#3B82F6',
					600: '#0B61EE',
					700: '#084BB8',
					800: '#063583',
					900: '#041F4D'
				},
				secondary: {
					DEFAULT: '#10B981',
					50: '#A7F3D0',
					100: '#6EE7B7',
					200: '#34D399',
					300: '#10B981',
					400: '#059669',
					500: '#047857',
					600: '#065F46',
					700: '#064E3B',
					800: '#022C22',
					900: '#011714'
				}
			}
		}
	},
	plugins: []
};
