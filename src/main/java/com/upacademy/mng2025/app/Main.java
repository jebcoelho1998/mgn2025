package com.upacademy.mng2025.app;

import com.upacademy.mng2025.entity.Product;
import com.upacademy.mng2025.entity.Shelf;
import com.upacademy.mng2025.repo.JDBCProductRepository;
import com.upacademy.mng2025.repo.JDBCShelfRepository;
import com.upacademy.mng2025.repo.ProductRepository;
import com.upacademy.mng2025.repo.ShelfRepository;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        ProductRepository prodRepo = new JDBCProductRepository();
        ShelfRepository   shelfRepo = new JDBCShelfRepository();

        while (true) {
            System.out.println("\nMenu Principal:");
            System.out.println("1) Listar produtos");
            System.out.println("2) Listar prateleiras");
            System.out.println("3) Sair");
            System.out.print("Opção: ");
            switch (sc.nextLine().trim()) {
                case "1": productMenu(prodRepo, shelfRepo); break;
                case "2": shelfMenu(shelfRepo, prodRepo);   break;
                case "3": System.out.println("Até à próxima!"); return;
                default:  System.out.println("Opção inválida.");
            }
        }
    }

    private static void productMenu(ProductRepository pr, ShelfRepository sr) {
        try {
            while (true) {
                System.out.println("\n=== Produtos ===");
                pr.findAll().forEach(p -> System.out.println("• " + p.getId()));
                System.out.println("1) Criar  2) Editar  3) Ver detalhe  4) Remover  5) Voltar");
                System.out.print("Opção: ");
                switch (sc.nextLine().trim()) {
                    case "1": createProduct(pr, sr);      break;
                    case "2": editProduct(pr);            break;
                    case "3": showProductDetail(pr, sr);  break;
                    case "4": deleteProduct(pr, sr);      break;
                    case "5": return;
                    default:  System.out.println("Inválido.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createProduct(ProductRepository pr, ShelfRepository sr) throws SQLException {
        System.out.print("ID: ");
        String id = sc.nextLine().trim();

        System.out.print("Desconto unitário: ");
        double desconto = Double.parseDouble(sc.nextLine().trim());

        System.out.println("Escolha a região para aplicar o IVA:");
        System.out.println("1) Portugal Continental (23%)");
        System.out.println("2) Madeira (22%)");
        System.out.println("3) Açores (18%)");
        System.out.print("Opção: ");
        String reg = sc.nextLine().trim();
        double iva;
        switch (reg) {
            case "2": iva = 22.0; break;
            case "3": iva = 18.0; break;
            default:  iva = 23.0; // cobre "1" e entradas inválidas
        }

        System.out.print("PVP: ");
        double pvp = Double.parseDouble(sc.nextLine().trim());

        pr.insert(new Product(id, desconto, iva, pvp));
        System.out.println("Criado. IVA aplicado: " + iva + "%.");

        System.out.print("Associar este produto a uma prateleira? (S/N): ");
        if ("S".equalsIgnoreCase(sc.nextLine().trim())) {
            System.out.print("ID da prateleira: ");
            String shelfId = sc.nextLine().trim();

            System.out.print("Capacidade: ");
            int capacidade = Integer.parseInt(sc.nextLine().trim());

            System.out.print("Preço aluguer diário: ");
            double rent = Double.parseDouble(sc.nextLine().trim());

            sr.insert(new Shelf(shelfId, capacidade, rent, id));
            System.out.println("Prateleira criada e associada ao produto.");
        }
    }

    private static void editProduct(ProductRepository pr) throws SQLException {
        System.out.print("ID do produto a editar: ");
        String id = sc.nextLine().trim();
        Product p = pr.findById(id);
        if (p == null) { System.out.println("Não existe."); return; }

        System.out.print("Desconto [" + p.getUnitDiscount() + "]: ");
        String s1 = sc.nextLine().trim();
        if (!s1.isEmpty()) p.setUnitDiscount(Double.parseDouble(s1));

        System.out.print("IVA [" + p.getVatPercent() + "]: ");
        String s2 = sc.nextLine().trim();
        if (!s2.isEmpty()) p.setVatPercent(Double.parseDouble(s2));

        System.out.print("PVP [" + p.getPvp() + "]: ");
        String s3 = sc.nextLine().trim();
        if (!s3.isEmpty()) p.setPvp(Double.parseDouble(s3));

        pr.update(p);
        System.out.println("Atualizado.");
    }

    private static void showProductDetail(ProductRepository pr, ShelfRepository sr) throws SQLException {
        System.out.print("ID do produto: ");
        String id = sc.nextLine().trim();
        Product p = pr.findById(id);
        if (p == null) { System.out.println("Não existe."); return; }

        System.out.println(p);
        System.out.println("Prateleiras:");
        sr.findAll().forEach(s -> {
            if (id.equals(s.getProductId())) {
                System.out.println(" - " + s.getId());
            }
        });
    }

    private static void deleteProduct(ProductRepository pr, ShelfRepository sr) throws SQLException {
        System.out.print("ID do produto: ");
        String id = sc.nextLine().trim();
        System.out.print("Confirmar (S/N): ");
        if ("S".equalsIgnoreCase(sc.nextLine().trim())) {
            for (Shelf s : sr.findAll()) {
                if (id.equals(s.getProductId())) {
                    s.setProductId(null);
                    sr.update(s);
                }
            }
            pr.delete(id);
            System.out.println("Removido.");
        }
    }

    private static void shelfMenu(ShelfRepository sr, ProductRepository pr) {
        try {
            while (true) {
                System.out.println("\n=== Prateleiras ===");
                sr.findAll().forEach(s -> System.out.println("• " + s.getId()));
                System.out.println("1) Criar 2) Editar 3) Ver detalhe 4) Remover 5) Voltar");
                System.out.print("Opção: ");
                switch (sc.nextLine().trim()) {
                    case "1": createShelf(sr);      break;
                    case "2": editShelf(sr);        break;
                    case "3": showShelfDetail(sr, pr); break;
                    case "4": deleteShelf(sr);      break;
                    case "5": return;
                    default:  System.out.println("Inválido.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createShelf(ShelfRepository sr) throws SQLException {
        System.out.print("ID: ");
        String id = sc.nextLine().trim();
        System.out.print("Capacidade: ");
        int cap = Integer.parseInt(sc.nextLine().trim());
        System.out.print("Preço aluguer: ");
        double rent = Double.parseDouble(sc.nextLine().trim());
        System.out.print("ID produto (ENTER = nenhum): ");
        String pid = sc.nextLine().trim();
        sr.insert(new Shelf(id, cap, rent, pid.isEmpty() ? null : pid));
        System.out.println("Criada.");
    }

    private static void editShelf(ShelfRepository sr) throws SQLException {
        System.out.print("ID da prateleira: ");
        String id = sc.nextLine().trim();
        Shelf s = sr.findById(id);
        if (s == null) { System.out.println("Não existe."); return; }

        System.out.print("Capacidade [" + s.getCapacity() + "]: ");
        String e1 = sc.nextLine().trim();
        if (!e1.isEmpty()) s.setCapacity(Integer.parseInt(e1));

        System.out.print("Preço aluguer [" + s.getDailyRentPrice() + "]: ");
        String e2 = sc.nextLine().trim();
        if (!e2.isEmpty()) s.setDailyRentPrice(Double.parseDouble(e2));

        System.out.print("ID produto [" + s.getProductId() + "] (ENTER limpa): ");
        String e3 = sc.nextLine().trim();
        s.setProductId(e3.isEmpty() ? null : e3);

        sr.update(s);
        System.out.println("Atualizada.");
    }

    private static void showShelfDetail(ShelfRepository sr, ProductRepository pr) throws SQLException {
        System.out.print("ID da prateleira: ");
        String id = sc.nextLine().trim();
        Shelf s = sr.findById(id);
        if (s == null) { System.out.println("Não existe."); return; }

        System.out.println(s);
        if (s.getProductId() != null) {
            System.out.println("Produto: " + pr.findById(s.getProductId()));
        }
    }

    private static void deleteShelf(ShelfRepository sr) throws SQLException {
        System.out.print("ID da prateleira: ");
        String id = sc.nextLine().trim();
        System.out.print("Confirmar (S/N): ");
        if ("S".equalsIgnoreCase(sc.nextLine().trim())) {
            sr.delete(id);
            System.out.println("Removida.");
        }
    }
}


