<script>
	export let settings = {
		title: 'Search',
		placeholder: 'Search...',
		buttonText: 'Search'
	};
	
	let searchQuery = '';
	
	function handleSearch(e) {
		e.preventDefault();
		if (searchQuery.trim()) {
			// Navigate to search results page
			window.location.href = `/search?q=${encodeURIComponent(searchQuery)}`;
		}
	}
</script>

<div class="widget search-widget">
	{#if settings.title}
		<h3 class="widget-title">{settings.title}</h3>
	{/if}
	<form on:submit={handleSearch} class="search-form">
		<input 
			type="text" 
			bind:value={searchQuery}
			placeholder={settings.placeholder}
			class="search-input"
		/>
		<button type="submit" class="search-button">
			{settings.buttonText}
		</button>
	</form>
</div>

<style>
	.search-form {
		display: flex;
		gap: 0.5rem;
	}
	
	.search-input {
		flex: 1;
		padding: 0.5rem 0.75rem;
		border: 1px solid #d1d5db;
		border-radius: 0.375rem;
		font-size: 0.875rem;
	}
	
	.search-input:focus {
		outline: none;
		border-color: var(--primary-color, #3B82F6);
		box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
	}
	
	.search-button {
		padding: 0.5rem 1rem;
		background: var(--primary-color, #3B82F6);
		color: white;
		border: none;
		border-radius: 0.375rem;
		font-size: 0.875rem;
		font-weight: 500;
		cursor: pointer;
		transition: background 0.2s;
	}
	
	.search-button:hover {
		background: var(--primary-dark, #2563EB);
	}
</style>
