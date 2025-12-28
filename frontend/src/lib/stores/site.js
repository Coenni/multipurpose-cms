import { writable } from 'svelte/store';

function createSiteStore() {
	const { subscribe, set, update } = writable({
		id: 1, // Default site ID
		name: 'Default Site',
		domain: 'localhost',
		loading: false,
		error: null
	});

	return {
		subscribe,
		setSite: (site) => set({ ...site, loading: false, error: null }),
		setLoading: (loading) => update(state => ({ ...state, loading })),
		setError: (error) => update(state => ({ ...state, error })),
		reset: () => set({ id: 1, name: 'Default Site', domain: 'localhost', loading: false, error: null })
	};
}

export const siteStore = createSiteStore();
