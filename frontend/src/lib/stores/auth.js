import { writable } from 'svelte/store';

function createAuthStore() {
	const { subscribe, set, update } = writable({
		user: null,
		token: null,
		isAuthenticated: false,
		loading: false,
		error: null
	});

	return {
		subscribe,
		login: (user, token) => set({ 
			user, 
			token, 
			isAuthenticated: true, 
			loading: false, 
			error: null 
		}),
		logout: () => set({ 
			user: null, 
			token: null, 
			isAuthenticated: false, 
			loading: false, 
			error: null 
		}),
		setLoading: (loading) => update(state => ({ ...state, loading })),
		setError: (error) => update(state => ({ ...state, error })),
		reset: () => set({ 
			user: null, 
			token: null, 
			isAuthenticated: false, 
			loading: false, 
			error: null 
		})
	};
}

export const authStore = createAuthStore();
