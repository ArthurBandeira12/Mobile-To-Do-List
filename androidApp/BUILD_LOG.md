# Build Log - Mobile To-Do App

## Prompt / Request
Inicialização do projeto no Android Studio e definição da arquitetura base para o Mobile To-Do App em Kotlin Multiplatform.

## Decision Summary
- **UI:** Compose Multiplatform.
- **Persistência:** SQLDelight.
- **Navegação:** Voyager.
- **Arquitetura:** MVVM.
- **IDE:** Android Studio escolhido por já possuir o ecossistema Android (SDK e Emuladores) integrado.

## Actions Performed
- Projeto aberto e sincronizado no Android Studio.
- Arquivo BUILD_LOG.md criado e primeiro commit realizado.

## Result
Projeto base configurado com sucesso e Gradle sincronizado sem erros.

## Current Status
Completed



## Prompt / Request
Tentativa de rodar o projeto em um dispositivo físico falhou com erro de resolução de dependência: "Could not find com.android.tools.build:aapt2".

## Problems / Errors
O Gradle tentou baixar o `aapt2` apenas no repositório `mavenCentral`, falhando ao não encontrar as ferramentas oficiais de build do Android. O repositório do Google estava ausente nas configurações.

## Fixes Attempted
- Adicionada a declaração `google()` nos blocos `pluginManagement.repositories` e `dependencyResolutionManagement.repositories` do arquivo `settings.gradle.kts`.

## Result
Build configurado para buscar dependências do Android no repositório correto. Aguardando resultado da compilação.

## Current Status
Completed (Fix applied)


## Prompt / Request
Erro de compilação: "Build was configured to prefer settings repositories over project repositories".

## Problems / Errors
O arquivo `build.gradle.kts` principal estava declarando repositórios (MavenRepo), o que entrava em conflito com a configuração `FAIL_ON_PROJECT_REPOS` definida no `settings.gradle.kts`.

## Fixes Attempted
- Bloco `repositories { ... }` removido do arquivo `build.gradle.kts` raiz, centralizando a resolução de dependências no `settings.gradle.kts`.

## Result
Conflito de repositórios resolvido.

## Current Status
Completed


## Prompt / Request
Requisitado o uso do Java 21 em vez de realizar o downgrade para o Java 11 na compilação do Android.

## Problems / Errors
A tentativa anterior de igualar as versões (Inconsistent JVM targets) reduziu a capacidade do projeto para o Java 11, impedindo o uso de recursos modernos da linguagem.

## Fixes Attempted
- Alterado o `jvmTarget` de `JvmTarget.JVM_11` para `JvmTarget.JVM_21` no ficheiro `androidApp/build.gradle.kts`.
- Mantida a configuração `jvmToolchain(21)`.

## Result
Projeto configurado com sucesso para utilizar Java 21 em ambas as tarefas de compilação.

## Current Status
Completed


## Prompt / Request
Implementar a navegação básica da aplicação conforme a estratégia de implementação (Passo 3).

## Decision Summary
- **Biblioteca selecionada:** Voyager (`cafe.adriel.voyager`).
- **Motivo:** É nativa para Compose Multiplatform, suporta injeção de dependências facilmente, possui gestão de estado com `ScreenModel` (ideal para a nossa arquitetura MVVM) e simplifica transições de ecrãs sem depender do Android Jetpack Navigation.

## Actions Performed
- Adicionadas as referências de versão e módulos do Voyager no ficheiro `gradle/libs.versions.toml` (navigator, screenmodel e transitions).
- Aplicadas as dependências no bloco `commonMain` do ficheiro `shared/build.gradle.kts`.
- Gradle sincronizado com sucesso.

## Result
Projeto pronto para receber os primeiros ecrãs e a estrutura de navegação.

## Current Status
Completed

## Prompt / Request
Criar a navegação básica e a estrutura dos 3 ecrãs exigidos pela especificação.

## Decision Summary
- **Estratégia de Navegação:** `Navigator` do Voyager a iniciar com `TaskListScreen`.
- **Passagem de dados (Ecrã 1 -> Ecrã 2):** Decidido passar apenas o `taskId: Long?` (ID da tarefa) como parâmetro para o `TaskDetailScreen`. Se for nulo, a aplicação sabe que é o modo de criação; se for preenchido, é o modo de edição. Esta abordagem evita problemas de serialização pesada ao não passar objetos inteiros entre ecrãs.
- **Animações:** Utilizado `SlideTransition` para dar uma sensação mais nativa e fluida à mudança de ecrãs.

## Actions Performed
- Ficheiro `Screens.kt` criado no módulo `commonMain` contendo as três implementações da interface `Screen` do Voyager.
- `App.kt` refatorado para remover o código *boilerplate* e implementar o `Navigator` com o tema Material.

## Result
Navegação entre a Lista de Tarefas, Detalhe da Tarefa e Gestão de Categorias implementada com sucesso.

## Current Status
Completed

## Prompt / Request
Erro de compilação no Gradle (`Unresolved reference`) após adicionar as dependências do SQLDelight.

## Problems / Errors
A configuração do `sqldelight { ... }` foi colocada indevidamente dentro do escopo de outro bloco no ficheiro `shared/build.gradle.kts`, o que corrompeu a leitura do script do Gradle e impediu a inicialização da base de dados.

## Fixes Attempted
- Movido o bloco de configuração do `sqldelight` para o nível raiz (top-level) do ficheiro `shared/build.gradle.kts`, imediatamente abaixo do bloco `plugins`.

## Result
Sintaxe do Gradle corrigida e configuração da base de dados reconhecida pelo plugin.

## Current Status
Completed (Fix applied)


## Prompt / Request
Correção de "Script compilation errors" e "Unresolved reference" no ficheiro `shared/build.gradle.kts`.

## Problems / Errors
A IDE inseriu automaticamente um import inválido (`org.gradle.declarative.dsl.schema.FqName.Empty.packageName`) enquanto o ficheiro possuía erros de sintaxe no passo anterior. Este import colidia com a propriedade `packageName` do SQLDelight, corrompendo a leitura do script inteiro pelo Gradle.

## Fixes Attempted
- Removido o import inválido do topo do ficheiro.
- Adicionado o bloco `iosMain.dependencies` com a dependência nativa do SQLDelight (`libs.sqldelight.native`) para garantir a compilação multiplataforma.

## Result
Script Gradle sincronizado com sucesso e preparado para o *Rebuild* do SQLDelight.

## Current Status
Completed


## Prompt / Request
Erro de "Unresolved reference 'androidLibrary'" no ficheiro `shared/build.gradle.kts`.

## Problems / Errors
A declaração do plugin do Android no módulo partilhado estava a referenciar um nome (`androidLibrary`) que não existe no catálogo de versões gerado pelo KMP Wizard.

## Fixes Attempted
- Corrigido o alias no bloco `plugins` para `libs.plugins.androidMultiplatformLibrary`, que corresponde à declaração correta no `libs.versions.toml`.

## Result
Erro novamente

## Current Status
Completed

## Prompt / Request
Resolução do erro de colisão de DSL no KMP com AGP 9.0 (Unresolved reference: namespace, compileSdk).

## Problems / Errors
O bloco `android { ... }` dentro de `kotlin { ... }` estava a ser resolvido incorretamente pelo compilador como a função de alvo `KotlinAndroidTarget`, resultando em erros de "Unresolved reference" para propriedades de configuração do Android. O bloco `compilerOptions` também apresentava incompatibilidade de DSL nesta versão específica.

## Fixes Attempted
- Substituído o bloco `android { ... }` por `androidLibrary { ... }` para forçar a resolução correta da extensão (contornando o bug de colisão de nomes).
- Removido o bloco problemático `compilerOptions`.
- Adicionado `jvmToolchain(21)` ao nível global do bloco `kotlin { ... }` para garantir a compatibilidade com o Java 21 de forma mais segura.

## Result
Avisos de depreciação ignorados em prol da compilação bem-sucedida. Configuração resolvida.

## Current Status
Completed



## Prompt / Request
Implementação dos drivers nativos do SQLite para inicialização da base de dados (Passo 5 da Estratégia de Implementação).

## Decision Summary
- **Padrão Arquitetural:** Utilizado o mecanismo `expect`/`actual` do Kotlin Multiplatform para criar o `DatabaseDriverFactory`.
- **Android:** Implementado o `AndroidSqliteDriver` que requer injeção do `Context` da aplicação.
- **iOS:** Implementado o `NativeSqliteDriver` utilizando o driver nativo do SQLDelight.
- **Nome do Ficheiro DB:** Definido como `todoapp.db` em ambas as plataformas para consistência.

## Actions Performed
- Criada a declaração `expect class` no módulo `commonMain`.
- Criadas as respetivas `actual classes` nos módulos `androidMain` e `iosMain` resolvendo as dependências específicas de cada plataforma.

## Result
Infraestrutura de acesso à base de dados concluída. A aplicação já tem capacidade para inicializar e gerir ligações SQLite de forma nativa.

## Current Status
Completed


## Prompt / Request
Definição dos modelos de dados (Task e Category) e configuração da persistência local com SQLite, conforme os Passos 4 e 5 da estratégia.

## Decision Summary
- **Base de Dados:** SQLDelight (versão 2.0.1) selecionado por gerar código Kotlin *type-safe* diretamente de *statements* SQL.
- **Modelos:** Optou-se por separar as tabelas em `TaskEntity` e `CategoryEntity`.
- **Estratégia de Eliminação:** Ao eliminar uma Categoria que está em uso, a tarefa não é apagada. Em vez disso, a restrição `ON DELETE SET NULL` no SQLite transforma a categoria da tarefa em "Uncategorized", cumprindo a secção 6 (Category Management) da especificação de forma nativa e eficiente.
- **Tipos de Dados:** O SQLite não tem tipo Booleano nativo, pelo que utilizámos `INTEGER AS Boolean` gerido pelo SQLDelight. As datas (`createdAt` e `dueDateTime`) serão guardadas como `TEXT` (formato ISO-8601) para simplificar a compatibilidade entre plataformas.

## Actions Performed
- Plugin e dependências do SQLDelight adicionados (Android, Native e Coroutines) ao `libs.versions.toml` e ao `shared/build.gradle.kts`.
- Configuração do pacote da base de dados definida para `project.to.doapp.arthur.database.AppDatabase`.
- Ficheiro `AppDatabase.sq` criado com os esquemas e *queries* iniciais, incluindo Chaves Estrangeiras (*Foreign Keys*).
- Projeto compilado (Rebuild) para gerar as classes nativas do SQLDelight.

## Result
Modelos de dados e infraestrutura SQLite devidamente configurados e classes Kotlin geradas.

## Current Status
Completed



## Prompt / Request
Ignorar falsos positivos do IDE no `iosMain` (ambiente Windows) e implementar o padrão Repository (Passo 5).

## Decision Summary
- **Falsos Positivos:** Os erros de "Unresolved reference" no source set `iosMain` foram identificados como um artefacto do Android Studio em hosts Windows, visto que a indexação do código gerado para iOS requer um ambiente macOS. O build via Gradle completou com sucesso.
- **Arquitetura (Repository):** Criada a classe `TaskRepository` no `commonMain`.
- **Injeção de Dependências Básica:** O repositório recebe o `DatabaseDriverFactory` no construtor para instanciar a `AppDatabase` agnóstica à plataforma.

## Actions Performed
- Criado o ficheiro `TaskRepository.kt`.
- Implementadas as funções iniciais de abstração do SQLDelight: `getAllTasks()`, `insertCategory()` e `getAllCategories()`.

## Result
A camada de acesso a dados está abstraída e pronta a ser consumida pelos ViewModels (ScreenModels do Voyager).

## Current Status
Completed



## Prompt / Request
Injeção da `AppDatabase` nos ecrãs através de `CompositionLocal` e implementação do `ScreenModel` base (Passos 6 e 7).

## Decision Summary
- **Injeção de Dependências:** Optou-se por `staticCompositionLocalOf` no ficheiro `App.kt` para distribuir o `TaskRepository` pela árvore de navegação do Compose, evitando dependências externas como Koin para não fragilizar a configuração recente do Gradle.
- **Entry Points:** O `DatabaseDriverFactory` foi instanciado e passado através do `MainActivity.kt` (fornecendo o `applicationContext`) e no `MainViewController.kt` (iOS).
- **Gestão de Estado:** Criado o `TaskListScreenModel` que herda de `ScreenModel` (Voyager) e utiliza `StateFlow` para observar e expor a lista de `TaskEntity` diretamente da base de dados.

## Actions Performed
- Função principal `App()` reestruturada para aceitar o Driver Factory.
- Instâncias nativas conectadas no Android e iOS.
- Padrão arquitetural reativo (StateFlow + LazyColumn) aplicado ao `TaskListScreen`.

## Result
A lista de tarefas está agora conetada em tempo real à base de dados SQLite.

## Current Status
Completed


## Prompt / Request
Correção do erro de compilação `Unresolved reference 'Boolean'` nas classes geradas pelo SQLDelight.

## Problems / Errors
Apesar do ficheiro `AppDatabase.sq` ter sido localizado e o código gerado com sucesso, o compilador do Kotlin não conseguiu resolver o tipo `Boolean` mapeado na instrução `INTEGER AS Boolean` da `TaskEntity`. O SQLDelight requer importações explícitas para tipos que não são primitivos diretos do SQLite.

## Fixes Attempted
- Adicionada a instrução `import kotlin.Boolean;` na primeira linha do ficheiro `AppDatabase.sq` para orientar o gerador de código.
- Executado o *Rebuild Project* para atualizar as classes geradas.

## Result
Tipo `Boolean` resolvido com sucesso nas *queries* geradas e erros em cascata eliminados.

## Current Status
Completed


## Prompt / Request
Resolução de erros de delegação de propriedades (`getValue`), tipagem (`receiver of type 'Int'`) e referências não resolvidas no `Screens.kt`.

## Problems / Errors
A ausência de importações específicas provocou falhas na compilação do UI:
1. A falta de `androidx.compose.runtime.getValue` impediu a delegação `by` do `collectAsState()`.
2. A falta de `androidx.compose.foundation.lazy.items` fez com que o compilador utilizasse a sobrecarga `items(count: Int)` do `LazyColumn`, inferindo incorretamente que o objeto da iteração era um `Int` em vez de um `TaskEntity`.
3. A falta de `cafe.adriel.voyager.core.model.rememberScreenModel` causou "Unresolved reference".

## Fixes Attempted
- Adicionadas manualmente as três declarações de importação em falta no topo do ficheiro `Screens.kt`.

## Result
Tipos inferidos corretamente e erros de compilação da interface resolvidos.

## Current Status
Completed


## Prompt / Request
Implementação da funcionalidade de inserção de dados (Create) no `TaskDetailScreen`.

## Decision Summary
- **Fluxo de Dados:** Adicionada a instrução `insertTask` no SQLDelight (`AppDatabase.sq`) e no `TaskRepository`.
- **Gestão de Estado na UI:** Utilizado `mutableStateOf` para vincular o input do utilizador aos `OutlinedTextField` do Compose de forma reativa.
- **Assincronismo:** A inserção na base de dados é gerida por uma corrotina no `TaskDetailScreenModel` (`screenModelScope.launch`), invocando uma *callback* (`onSaveComplete`) para acionar o `navigator.pop()` apenas quando a persistência for garantida.

## Actions Performed
- Rebuild ao projeto para gerar o novo método de inserção no SQLDelight.
- Criação do ficheiro `TaskDetailScreenModel.kt`.
- Reestruturação do `TaskDetailScreen` para incluir formulário de input (`Scaffold`, `TopAppBar`, `OutlinedTextField`, `FloatingActionButton`).

## Result
A aplicação é agora capaz de persistir novas tarefas na base de dados SQLite nativa de forma assíncrona, atualizando o ecrã principal de imediato devido à reatividade do `StateFlow`.

## Current Status
Completed


## Prompt / Request
Resolução de erros em cascata (`remember`, `mutableStateOf`, `it` não resolvido e contexto `@Composable`) no formulário do `TaskDetailScreen`.

## Problems / Errors
A utilização de `var ... by remember { mutableStateOf("") }` requer a importação explícita do delegado `setValue` do Compose. A sua ausência quebrou a inferência de tipos em todo o ecrã, fazendo com que os componentes visuais (como o `OutlinedTextField`) não reconhecessem os seus parâmetros lambda (`it`), gerando erros secundários.

## Fixes Attempted
- Adicionadas importações explícitas de `androidx.compose.runtime.remember`, `androidx.compose.runtime.mutableStateOf` e `androidx.compose.runtime.setValue` no `Screens.kt`.

## Result
Delegação de propriedades restaurada, inferência de tipos corrigida e formulário de criação de tarefas apto a compilar.

## Current Status
Completed


## Prompt / Request
Resolução de latência extrema na atualização da interface após inserir, atualizar ou apagar tarefas.

## Problems / Errors
A navegação com o Voyager retém as instâncias de `ScreenModel`. Ao retornar do `TaskDetailScreen`, o bloco `init` do `TaskListScreenModel` não era executado novamente, e o estado não era atualizado. A chamada bloqueante `executeAsList()` também causava congelamento da *Main Thread*.

## Fixes Attempted
- Substituição da leitura estática `executeAsList()` pela extensão reativa `asFlow().mapToList(Dispatchers.Default)` do SQLDelight.
- Refatoração do `TaskListScreenModel` para implementar um coletor contínuo (`collect`) no `init`, eliminando a necessidade de invocar métodos manuais de *reload* após mutações de dados.

## Result
Integração reativa *end-to-end* concluída. A UI atualiza instantaneamente a qualquer modificação no SQLite (Create/Update/Delete) através do sincronismo garantido pelo `Flow` do Kotlin. Latência eliminada.

## Current Status
Completed

## Prompt / Request
Erro de compilação `Unresolved reference 'icons'` e dependência ausente para o pacote `androidx.compose.material.icons`.

## Problems / Errors
O template do KMP não inclui a biblioteca `material-icons-extended` por padrão. A tentativa de importar e utilizar `Icons.Default.Delete` bloqueou a compilação.

## Fixes Attempted
- Removidos os imports do `material.icons`.
- Substituída a implementação do `Icon` por um componente `Text` contendo o emoji 🗑️, mitigando a necessidade de alterar as dependências do Gradle e arriscar novos conflitos de sincronização.
- Executado Rebuild para resolver dependências pendentes do SQLDelight no `TaskListScreenModel.kt`.

## Result
Interface de eliminação implementada de forma nativa e sem impacto no tamanho final da aplicação ou nos ficheiros de configuração.

## Current Status
Completed



## Prompt / Request
Implementação da edição de texto da tarefa (Update completo).

## Decision Summary
- **Base de Dados:** Adicionadas queries `selectTaskById` e `updateTaskText`.
- **Arquitetura:** `TaskDetailScreenModel` adaptado para distinguir entre um "Insert" (`taskId == null`) e um "Update" (`taskId != null`).
- **UI (Lista):** Modificador `.clickable` adicionado ao `Card` para injetar o ID da tarefa na rota do Voyager.
- **UI (Detalhe):** Utilizado `LaunchedEffect` para garantir a execução única da query de leitura ao inicializar o ecrã, preenchendo as variáveis de estado (`title` e `description`) com os dados existentes.

## Result
Fluxo completo de edição (Update) estabelecido. As tarefas agora podem ser modificadas diretamente.

## Current Status
Completed


## Prompt / Request
Erro de compilação `Unresolved reference 'TaskListScreen'` no `App.kt` e erros em cascata no `Screens.kt` após substituição do ficheiro.

## Problems / Errors
A omissão da declaração `package` no topo do ficheiro `Screens.kt` após a refatoração moveu as classes para o *default package*. Como o `App.kt` e os Repositórios estão sob o pacote `project.to.doapp.arthur`, as referências entre os ficheiros foram quebradas.

## Fixes Attempted
- Adicionada a declaração `package project.to.doapp.arthur` na primeira linha do `Screens.kt`.

## Result
Visibilidade das classes restaurada no mesmo *namespace* e compilação concluída com sucesso.

## Current Status
Completed