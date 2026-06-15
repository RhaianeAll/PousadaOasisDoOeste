# 💻 Pousada Oásis do Oeste
> Projeto desenvolvido durante as aulas de Programação Orientada a Objetos (POO).

## ⚠️ **Informações Necessárias para Rodar o Projeto**

Certifique-se de ter as seguintes ferramentas instaladas em sua máquina:

* **Java Development Kit (JDK)**
* Uma IDE ou editor de código de sua preferência (recomenda-se **NetBeans** ou **VS Code**).

---

## 🛠️ Executando no VS Code (Atenção à compatibilidade)

Como este projeto foi originalmente desenvolvido utilizando o **NetBeans**, algumas configurações podem ser necessárias ao abri-lo no **VS Code**:

### 1. Sincronização da Versão do Java (`pom.xml`)
* Verifique o arquivo `pom.xml`. Caso a versão do Java especificada no arquivo seja **diferente da versão instalada em sua máquina**, atualize o arquivo com a sua versão atual.
* Assim que você **salvar o arquivo** (`Ctrl + S`), o VS Code exibirá um pequeno pop-up no canto inferior direito com a mensagem:
  > *"A build file was modified. Do you want to synchronize..."*
* **Clique em "Yes" ou "Always"**.
* *(Se o pop-up não aparecer, clique com o botão direito em cima do arquivo `pom.xml` e selecione **"Update Project Configuration"**).*

### 2. Limpeza do Workspace (Prevenção de Erros)
Para garantir que o VS Code rode o projeto sem problemas de cache:
1. Pressione `Ctrl + Shift + P` no teclado.
2. Digite `Java: Clean Workspace` e aperte `Enter`.
3. Confirme clicando em **"Restart and Clean"**.
4. Quando o VS Code terminar de recarregar, abra a sua classe `SistemaPousadaUI.java` e clique em **Run** novamente.

Pronto! Seguindo esses passos, tudo deve ocorrer perfeitamente no VS Code.

---

## 🧪 Testes de Funcionamento

Para testar as funcionalidades do sistema, utilize o arquivo de texto incluso no diretório do projeto:

* 📄 `acomodacoes_servicos.txt`

> ⚠️ **Nota:** Certifique-se de que o arquivo esteja no diretório correto indicado pelo código para que o sistema consiga realizar a leitura dos dados corretamente.
