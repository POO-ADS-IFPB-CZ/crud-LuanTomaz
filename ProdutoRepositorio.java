import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoRepositorio {
    private static final String ARQUIVO = "produtos.dat";
    private List<Produto> produtos;

    public ProdutoRepositorio() {
        produtos = carregar();
    }

    public List<Produto> listar() {
        return produtos;
    }

    public void salvar(Produto produto) {
        produtos.add(produto);
        gravar();
    }

    public void atualizar(Produto produto) {
        int index = produtos.indexOf(produto);
        if (index != -1) {
            produtos.set(index, produto);
            gravar();
        }
    }

    public void remover(Produto produto) {
        produtos.remove(produto);
        gravar();
    }

    private void gravar() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(produtos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private List<Produto> carregar() {
        File file = new File(ARQUIVO);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO))) {
            return (List<Produto>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
