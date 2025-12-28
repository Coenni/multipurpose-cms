<script>
	export let items = [
		{ label: 'Home', href: '/' },
		{ label: 'Themes', href: '/admin/themes' },
		{ label: 'Plugins', href: '/admin/plugins' },
		{ label: 'Pages', href: '/admin/pages' },
		{ label: 'Posts', href: '/admin/posts' }
	];
	
	let isOpen = false;
	
	function toggleMenu() {
		isOpen = !isOpen;
	}
</script>

<nav class="bg-primary text-white">
	<div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
		<div class="flex items-center justify-between h-12">
			<div class="hidden md:block">
				<div class="flex items-center space-x-4">
					{#each items as item}
						<a 
							href={item.href} 
							class="px-3 py-2 rounded-md text-sm font-medium hover:bg-primary-600 transition-colors"
						>
							{item.label}
						</a>
					{/each}
				</div>
			</div>
			<div class="md:hidden">
				<button 
					on:click={toggleMenu}
					class="inline-flex items-center justify-center p-2 rounded-md hover:bg-primary-600 focus:outline-none"
				>
					<svg class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
						{#if isOpen}
							<path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
						{:else}
							<path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16" />
						{/if}
					</svg>
				</button>
			</div>
		</div>
	</div>
	
	{#if isOpen}
		<div class="md:hidden">
			<div class="px-2 pt-2 pb-3 space-y-1">
				{#each items as item}
					<a 
						href={item.href}
						class="block px-3 py-2 rounded-md text-base font-medium hover:bg-primary-600 transition-colors"
					>
						{item.label}
					</a>
				{/each}
			</div>
		</div>
	{/if}
</nav>
