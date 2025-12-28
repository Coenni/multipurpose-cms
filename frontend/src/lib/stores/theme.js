import { writable } from 'svelte/store';

function createThemeStore() {
	const { subscribe, set, update } = writable({
		current: null,
		available: [],
		loading: false,
		error: null
	});

	return {
		subscribe,
		setCurrentTheme: (theme) => update(state => ({ ...state, current: theme })),
		setAvailableThemes: (themes) => update(state => ({ ...state, available: themes })),
		setLoading: (loading) => update(state => ({ ...state, loading })),
		setError: (error) => update(state => ({ ...state, error })),
		reset: () => set({ current: null, available: [], loading: false, error: null })
	};
}

export const themeStore = createThemeStore();
