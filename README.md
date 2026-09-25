## 🚀 Como Executar o Projeto

**Pré-requisitos:** Android Studio (versão recente) e JDK 21. Para testar no iOS, é necessário um Mac com Xcode.

**Passo a Passo:**

1. **Clonar e Abrir:**
  * Faça o clone do repositório: `git clone https://github.com/SEU-USUARIO/todo-app-kmp.git`
  * Abra a pasta clonada no **Android Studio**.

2. **Gerar a Base de Dados (Passo Obrigatório):**
  * Aguarde a sincronização inicial do Gradle.
  * No menu superior do Android Studio, clique em **Build > Rebuild Project**.
    *(Atenção: Este passo é essencial para o SQLDelight gerar as classes do SQLite. Sem isto, a aplicação não compila).*

3. **Correr no Android:**
  * Na barra superior, certifique-se de que a configuração está definida como **`composeApp`** ou **`androidApp`**.
  * Escolha um emulador ou ligue o seu dispositivo físico.
  * Clique em **Run** (o botão verde de "Play").

4. **Correr no iOS (Opcional):**
  * Mude a configuração de execução no topo para **`iosApp`**.
  * Escolha um simulador de iPhone e clique em **Run**.
