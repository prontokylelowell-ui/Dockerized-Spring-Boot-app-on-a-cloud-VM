const API_BASE = '/api/pokemon';

const pokemonGrid = document.getElementById('pokemonGrid');
const searchInput = document.getElementById('searchInput');
const typeFilter = document.getElementById('typeFilter');
const pokemonModal = document.getElementById('pokemonModal');
const modalClose = document.getElementById('modalClose');
const modalBody = document.getElementById('modalBody');

let allPokemon = [];

document.addEventListener('DOMContentLoaded', async () => {
    await loadTypes();
    await loadPokemon();

    searchInput.addEventListener('input', debounce(filterPokemon, 250));
    typeFilter.addEventListener('change', filterPokemon);
    modalClose.addEventListener('click', closeModal);
    pokemonModal.addEventListener('click', (e) => {
        if (e.target === pokemonModal) closeModal();
    });
    document.addEventListener('keydown', (e) => {
        if (e.key === 'Escape') closeModal();
    });
});

function debounce(func, wait) {
    let timeout;
    return function executedFunction(...args) {
        const later = () => {
            clearTimeout(timeout);
            func(...args);
        };
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
    };
}

async function loadTypes() {
    try {
        const res = await fetch(`${API_BASE}/types`);
        const types = await res.json();
        types.forEach(type => {
            const option = document.createElement('option');
            option.value = type;
            option.textContent = type;
            typeFilter.appendChild(option);
        });
    } catch (err) {
        console.error('Failed to load types:', err);
    }
}

async function loadPokemon() {
    try {
        const res = await fetch(API_BASE);
        allPokemon = await res.json();
        renderPokemon(allPokemon);
    } catch (err) {
        pokemonGrid.innerHTML = `
            <div class="no-results">
                Failed to load Pokémon data. Please ensure the API server is running.
            </div>
        `;
        console.error('Failed to load pokemon:', err);
    }
}

async function filterPokemon() {
    const name = searchInput.value.trim();
    const type = typeFilter.value;

    const params = new URLSearchParams();
    if (name) params.set('name', name);
    if (type) params.set('type', type);

    try {
        pokemonGrid.innerHTML = '<div class="loading-spinner"></div>';
        const res = await fetch(`${API_BASE}?${params.toString()}`);
        const filtered = await res.json();
        renderPokemon(filtered);
    } catch (err) {
        console.error('Failed to filter pokemon:', err);
    }
}

function renderPokemon(pokemonList) {
    if (pokemonList.length === 0) {
        pokemonGrid.innerHTML = `
            <div class="no-results">
                <p style="font-size: 3rem; margin-bottom: 15px;">🔍</p>
                <p>No Pokémon found matching your criteria.</p>
            </div>
        `;
        return;
    }

    pokemonGrid.innerHTML = pokemonList.map(p => `
        <div class="pokemon-card" data-id="${p.id}">
            <div class="pokemon-image">
                <img src="${p.imageUrl}" alt="${p.name}" loading="lazy"
                     onerror="this.src='data:image/svg+xml,<svg xmlns=%22http://www.w3.org/2000/svg%22 viewBox=%220 0 100 100%22><text y=%22.9em%22 font-size=%2290%22>🎮</text></svg>'">
            </div>
            <div class="pokemon-id">#${String(p.id).padStart(3, '0')}</div>
            <div class="pokemon-name">${p.name}</div>
            <div class="pokemon-types">
                ${p.types.map(t => `<span class="type-badge type-${t.toLowerCase()}">${t}</span>`).join('')}
            </div>
        </div>
    `).join('');

    pokemonGrid.querySelectorAll('.pokemon-card').forEach(card => {
        card.addEventListener('click', () => {
            const id = parseInt(card.dataset.id);
            const pokemon = allPokemon.find(p => p.id === id);
            if (pokemon) openModal(pokemon);
        });
    });
}

function openModal(p) {
    const totalStats = p.stats.hp + p.stats.attack + p.stats.defense +
                      p.stats.specialAttack + p.stats.specialDefense + p.stats.speed;

    modalBody.innerHTML = `
        <div class="modal-hero">
            <div class="modal-id">#${String(p.id).padStart(3, '0')}</div>
            <div class="modal-name">${p.name}</div>
            <div class="modal-image">
                <img src="${p.imageUrl}" alt="${p.name}">
            </div>
            <div class="pokemon-types" style="justify-content: center; margin-top: 15px;">
                ${p.types.map(t => `<span class="type-badge type-${t.toLowerCase()}">${t}</span>`).join('')}
            </div>
        </div>
        <div class="modal-body">
            <div class="modal-section">
                <div class="modal-section-title">Description</div>
                <p class="modal-description">${p.description}</p>
            </div>

            <div class="modal-section">
                <div class="modal-section-title">Physical Info</div>
                <div class="modal-info-grid">
                    <div class="info-card">
                        <div class="info-label">Height</div>
                        <div class="info-value">${(p.height / 10).toFixed(1)} m</div>
                    </div>
                    <div class="info-card">
                        <div class="info-label">Weight</div>
                        <div class="info-value">${(p.weight / 10).toFixed(1)} kg</div>
                    </div>
                    <div class="info-card">
                        <div class="info-label">Total Stats</div>
                        <div class="info-value">${totalStats}</div>
                    </div>
                    <div class="info-card">
                        <div class="info-label">Abilities</div>
                        <div class="info-value" style="font-size: 0.95rem;">${p.abilities.length}</div>
                    </div>
                </div>
            </div>

            <div class="modal-section">
                <div class="modal-section-title">Abilities</div>
                <div class="modal-tags">
                    ${p.abilities.map(a => `<span class="ability-tag">${a}</span>`).join('')}
                </div>
            </div>

            <div class="modal-section">
                <div class="modal-section-title">Base Stats</div>
                <div class="stats-container">
                    ${renderStat('HP', p.stats.hp)}
                    ${renderStat('Attack', p.stats.attack)}
                    ${renderStat('Defense', p.stats.defense)}
                    ${renderStat('Sp. Atk', p.stats.specialAttack)}
                    ${renderStat('Sp. Def', p.stats.specialDefense)}
                    ${renderStat('Speed', p.stats.speed)}
                </div>
            </div>
        </div>
    `;

    pokemonModal.style.display = 'flex';
    document.body.style.overflow = 'hidden';

    requestAnimationFrame(() => {
        modalBody.querySelectorAll('.stat-fill').forEach(fill => {
            fill.style.width = fill.dataset.width;
        });
    });
}

function renderStat(name, value) {
    const maxStat = 200;
    const percentage = Math.min((value / maxStat) * 100, 100);
    const level = value >= 120 ? 'high' : value >= 70 ? 'mid' : 'low';
    return `
        <div class="stat-row">
            <span class="stat-name">${name}</span>
            <span class="stat-value">${value}</span>
            <div class="stat-bar">
                <div class="stat-fill ${level}" data-width="${percentage}%" style="width: 0%"></div>
            </div>
        </div>
    `;
}

function closeModal() {
    pokemonModal.style.display = 'none';
    document.body.style.overflow = '';
}
