package cl.pixelandbean.service;

import cl.pixelandbean.model.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ProductService {
    private final List<Product> products = new ArrayList<>();
    private final AtomicInteger sequence = new AtomicInteger(1);

    public ProductService() {
        products.add(new Product(sequence.getAndIncrement(), "Espresso", "bebida", "Caliente", 2200, true));
        products.add(new Product(sequence.getAndIncrement(), "Café con leche", "bebida", "Caliente", 2500, true));
        products.add(new Product(sequence.getAndIncrement(), "Cappuccino", "bebida", "Caliente", 2600, true));
        products.add(new Product(sequence.getAndIncrement(), "Brownie", "snack", "Dulce", 1800, true));
        products.add(new Product(sequence.getAndIncrement(), "15 minutos Arcade", "tiempo-arcade", "Cabina retro", 1500, true));
        products.add(new Product(sequence.getAndIncrement(), "60 minutos Arcade", "tiempo-arcade", "Cabina retro", 5000, true));
    }

    public List<Product> search(String query) {
        if (query == null || query.isBlank()) {
            return new ArrayList<>(products);
        }
        final String normalized = query.toLowerCase();
        return products.stream()
                .filter(p -> p.getName().toLowerCase().contains(normalized)
                        || p.getCategory().toLowerCase().contains(normalized))
                .collect(Collectors.toList());
    }

    public void save(String name, String category, String type, double price, boolean active, Integer idToUpdate) {
        if (idToUpdate == null) {
            products.add(new Product(sequence.getAndIncrement(), name, category, type, price, active));
            return;
        }
        Optional<Product> existing = products.stream().filter(p -> p.getId() == idToUpdate).findFirst();
        if (existing.isEmpty()) {
            products.add(new Product(sequence.getAndIncrement(), name, category, type, price, active));
        } else {
            Product product = existing.get();
            product.setName(name);
            product.setCategory(category);
            product.setType(type);
            product.setPrice(price);
            product.setActive(active);
        }
    }

    public void delete(int id) {
        products.removeIf(p -> p.getId() == id);
    }
}
