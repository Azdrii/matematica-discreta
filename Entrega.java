import java.lang.AssertionError;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/*
 * Aquesta entrega consisteix en implementar tots els mètodes annotats amb "// TODO". L'enunciat de
 * cada un d'ells és al comentari de la seva signatura i exemples del seu funcionament als mètodes
 * `Tema1.tests`, `Tema2.tests`, etc.
 *
 * L'avaluació consistirà en:
 *
 * - Si el codi no compila, la nota del grup serà un 0.
 *
 * - Si heu fet cap modificació que no sigui afegir un mètode, afegir proves als mètodes "tests()" o
 *   implementar els mètodes annotats amb "// TODO", la nota del grup serà un 0.
 *
 * - Principalment, la nota dependrà del correcte funcionament dels mètodes implemnetats (provant
 *   amb diferents entrades).
 *
 * - Tendrem en compte la neteja i organització del codi. Un estandard que podeu seguir és la guia
 *   d'estil de Google per Java: https://google.github.io/styleguide/javaguide.html . Algunes
 *   consideracions importants:
 *    + Entregau amb la mateixa codificació (UTF-8) i finals de línia (LF, no CR+LF)
 *    + Indentació i espaiat consistent
 *    + Bona nomenclatura de variables
 *    + Declarar les variables el més aprop possible al primer ús (és a dir, evitau blocs de
 *      declaracions).
 *    + Convé utilitzar el for-each (for (int x : ...)) enlloc del clàssic (for (int i = 0; ...))
 *      sempre que no necessiteu l'índex del recorregut.
 *
 * Per com està plantejada aquesta entrega, no necessitau (ni podeu) utilitzar cap `import`
 * addicional, ni qualificar classes que no estiguin ja importades. El que sí podeu fer és definir
 * tots els mètodes addicionals que volgueu (de manera ordenada i dins el tema que pertoqui).
 *
 * Podeu fer aquesta entrega en grups de com a màxim 3 persones, i necessitareu com a minim Java 10.
 * Per entregar, posau a continuació els vostres noms i entregau únicament aquest fitxer.
 * - Nom 1: Andreu Massanet Felix
 * - Nom 2: Diego Malagrida Gonzalez
 * - Nom 3: Bàrbara Sierra Riera
 *
 * L'entrega es farà a través d'una tasca a l'Aula Digital que obrirem abans de la data que se us
 * hagui comunicat i vos recomanam que treballeu amb un fork d'aquest repositori per seguir més
 * fàcilment les actualitzacions amb enunciats nous. Si no podeu visualitzar bé algun enunciat,
 * assegurau-vos de que el vostre editor de texte estigui configurat amb codificació UTF-8.
 */
class Entrega {
  /*
   * Aquí teniu els exercicis del Tema 1 (Lògica).
   *
   * La majoria dels mètodes reben de paràmetre l'univers (representat com un array) i els predicats
   * adients (per exemple, `Predicate<Integer> p`). Per avaluar aquest predicat, si `x` és un
   * element de l'univers, podeu fer-ho com `p.test(x)`, que té com resultat un booleà (true si
   * `P(x)` és cert). Els predicats de dues variables són de tipus `BiPredicate<Integer, Integer>` i
   * similarment s'avaluen com `p.test(x, y)`.
   *
   * En cada un d'aquests exercicis (excepte el primer) us demanam que donat l'univers i els
   * predicats retorneu `true` o `false` segons si la proposició donada és certa (suposau que
   * l'univers és suficientment petit com per poder provar tots els casos que faci falta).
   */
  static class Tema1 {
    /*
     * Donat n > 1, en quants de casos (segons els valors de veritat de les proposicions p1,...,pn)
     * la proposició (...((p1 -> p2) -> p3) -> ...) -> pn és certa?
     *
     * Vegeu el mètode Tema1.tests() per exemples.
     */
    static int exercici1(int n) {
      System.out.println("<><> EXERCICI 1 <><>");
            int filas = 1 << n;
            int casos = 0;

            for (int i = 0; i < filas; i++) {
                boolean[] p = new boolean[n];

                for (int j = n - 1; j >= 0; j--) {
                    p[j] = (i & (1 << j)) != 0;
                }

                if (filaCumpleCadenaDeImplicaciones(p)) {
                    casos++;
                }
            }

            System.out.println("n: " + n + " - casos: " + casos);
            return casos;
    }

    static boolean filaCumpleCadenaDeImplicaciones(boolean[] p) {
      boolean resultado = p[0];
      for (int i = 1; i < p.length; i++) {
          resultado = !resultado || p[i];
      }
      return resultado;
  }

    /*
     * És cert que ∀x : P(x) -> ∃!y : Q(x,y) ?
     */
    static boolean exercici2(int[] universe, Predicate<Integer> p, BiPredicate<Integer, Integer> q) {
      System.out.println("<><> EXERCICI 2 <><>");
            for (int x : universe) {
                if (p.test(x)) {
                    int numY = 0;

                    for (int y : universe) {
                        if (q.test(x, y)) {
                            numY++;
                        }
                    }

                    if (numY != 1) {
                        System.out.println("false");
                        return false;
                    }
                }
            }

            System.out.println("true");
            return true;
    }

    /*
     * És cert que ∃x : ∀y : Q(x, y) -> P(x) ?
     */
    static boolean exercici3(int[] universe, Predicate<Integer> p, BiPredicate<Integer, Integer> q) {
      System.out.println("<><> EXERCICI 3 <><>");

            for (int x : universe) {
                boolean paraTodoY = true;

                for (int y : universe) {
                    if (q.test(x, y) && !p.test(x)) {
                        paraTodoY = false;
                        break;
                    }
                }

                if (paraTodoY) {
                    System.out.println("true");
                    return true;
                }
            }

            System.out.println("false");
            return false;
    }

    /*
     * És cert que ∃x : ∃!y : ∀z : P(x,z) <-> Q(y,z) ?
     */
    static boolean exercici4(int[] universe, BiPredicate<Integer, Integer> p, BiPredicate<Integer, Integer> q) {
      System.out.println("<><> EXERCICI 4 <><>");

            for (int x : universe) {
                boolean hayY = false;
                int numY = 0;

                for (int y : universe) {
                    boolean paraTodoZ = true;
                    for (int z : universe) {
                        if (p.test(x, z) != q.test(y, z)) {
                            paraTodoZ = false;
                            break;
                        }
                    }

                    if (paraTodoZ) {
                        numY++;
                        if (numY > 1) {
                            hayY = false;
                            break;
                        }
                        hayY = true;
                    }
                }

                if (hayY && numY == 1) {
                    System.out.println("true");
                    return true;
                }
            }

            System.out.println("false");
            return false;
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1

      // p1 -> p2 és cert exactament a 3 files
      // p1 p2
      // 0  0  <-
      // 0  1  <-
      // 1  0
      // 1  1  <-
      assertThat(exercici1(2) == 3);

      // (p1 -> p2) -> p3 és cert exactament a 5 files
      // p1 p2 p3
      // 0  0  0
      // 0  0  1  <-
      // 0  1  0
      // 0  1  1  <-
      // 1  0  0  <-
      // 1  0  1  <-
      // 1  1  0
      // 1  1  1  <-
      assertThat(exercici1(3) == 5);

      // Exercici 2
      // ∀x : P(x) -> ∃!y : Q(x,y)
      assertThat(
          exercici2(
            new int[] { 1, 2, 3 },
            x -> x % 2 == 0,
            (x, y) -> x+y >= 5
          )
      );

      assertThat(
          !exercici2(
            new int[] { 1, 2, 3 },
            x -> x < 3,
            (x, y) -> x-y > 0
          )
      );

      // Exercici 3
      // És cert que ∃x : ∀y : Q(x, y) -> P(x) ?
      assertThat(
          exercici3(
            new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 },
            x -> x % 3 != 0,
            (x, y) -> y % x == 0
          )
      );

      assertThat(
          exercici3(
            new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 },
            x -> x % 4 != 0,
            (x, y) -> (x*y) % 4 != 0
          )
      );

      // Exercici 4
      // És cert que ∃x : ∃!y : ∀z : P(x,z) <-> Q(y,z) ?
      assertThat(
          exercici4(
            new int[] { 0, 1, 2, 3, 4, 5 },
            (x, z) -> x*z == 1,
            (y, z) -> y*z == 2
          )
      );

      assertThat(
          !exercici4(
            new int[] { 2, 3, 4, 5 },
            (x, z) -> x*z == 1,
            (y, z) -> y*z == 2
          )
      );
    }
  }

  /*
   * Aquí teniu els exercicis del Tema 2 (Conjunts).
   *
   * Per senzillesa tractarem els conjunts com arrays (sense elements repetits). Per tant, un
   * conjunt de conjunts d'enters tendrà tipus int[][].
   *
   * Les relacions també les representarem com arrays de dues dimensions, on la segona dimensió
   * només té dos elements. Per exemple
   *   int[][] rel = {{0,0}, {0,1}, {1,1}, {2,2}};
   * i també donarem el conjunt on està definida, per exemple
   *   int[] a = {0,1,2};
   * Als tests utilitzarem extensivament la funció generateRel definida al final (també la podeu
   * utilitzar si la necessitau).
   *
   * Les funcions f : A -> B (on A i B son subconjunts dels enters) les representam o bé amb el seu
   * graf o amb un objecte de tipus Function<Integer, Integer>. Sempre donarem el domini int[] a, el
   * codomini int[] b. En el cas de tenir un objecte de tipus Function<Integer, Integer>, per aplicar
   * f a x, és a dir, "f(x)" on x és d'A i el resultat f.apply(x) és de B, s'escriu f.apply(x).
   */
  static class Tema2 {
    /*
     * Calculau el nombre d'elements del conjunt (a u b) × (a \ c)
     *
     * Podeu soposar que `a`, `b` i `c` estan ordenats de menor a major.
     */
    static int exercici1(int[] a, int[] b, int[] c) {
      System.out.println("<><> EXERCICI 1 <><>");

            int[] union = new int[a.length + b.length];
            int midaUnion = 0;
            for (int i : a) {
                if (!contiene(union, midaUnion, i)) {
                    union[midaUnion++] = i;
                }
            }
            for (int i : b) {
                if (!contiene(union, midaUnion, i)) {
                    union[midaUnion++] = i;
                }
            }

            int total = 0;
            for (int i = 0; i < midaUnion; i++) {
                for (int j : a) {
                    if (!contiene(c, c.length, j)) {
                        total++;
                    }
                }
            }

            System.out.println("elements del conjunt (a u b) × (a \\ c): " + total);
            return total;
    }

    static boolean contiene(int[] arr, int size, int element) {
      for (int i = 0; i < size; i++) {
          if (arr[i] == element) {
              return true;
          }
      }
      return false;
  }

    /*
     * La clausura d'equivalència d'una relació és el resultat de fer-hi la clausura reflexiva, simètrica i
     * transitiva simultàniament, i, per tant, sempre és una relació d'equivalència.
     *
     * Trobau el cardinal d'aquesta clausura.
     *
     * Podeu soposar que `a` i `rel` estan ordenats de menor a major (`rel` lexicogràficament).
     */
    static int exercici2(int[] a, int[][] rel) {
      System.out.println("<><> EXERCICI 2 <><>");

            //reflexiva
            for (int x : a) {
                boolean esta = false;
                for (int i = 0; i < rel.length; i++) {
                    if (rel[i][0] == x && rel[i][1] == x) {
                        esta = true;
                    }
                }

                if (!esta) {
                    rel = insertar(rel, x);
                }
            }

            //simetrica
            for (int i = 0; i < rel.length; i++) {
                int x = rel[i][0];
                int y = rel[i][1];
                boolean esta = false;
                for (int j = 0; j < rel.length; j++) {
                    if (rel[j][0] == y && rel[j][1] == x) {
                        esta = true;
                    }
                }

                if (!esta) {
                    rel = insertar(rel, y, x);
                }
            }

            //transitiva
            for (int i = 0; i < rel.length; i++) {
                int x = rel[i][0];
                int y = rel[i][1];
                for (int j = 0; j < rel.length; j++) {
                    boolean esta = false;
                    if (rel[j][0] == y) {
                        int z = rel[j][1];
                        for (int k = 0; k < rel.length; k++) {
                            if (rel[k][0] == x && rel[k][1] == z) {
                                esta = true;
                            }
                        }

                        if (!esta) {
                            rel = insertar(rel, x, z);
                        }
                    }
                }
            }

            System.out.println("cardinal de clausura d'equivalència: " + rel.length);
            return rel.length;
    }

    static int[][] insertar(int[][] rel, int x) {
      return insertar(rel, x, x);
  }

  static int[][] insertar(int[][] rel, int x, int y) {
      int[][] relNueva = new int[rel.length + 1][2];

      for (int i = 0; i < rel.length; i++) {
          relNueva[i] = rel[i];
      }

      relNueva[rel.length][0] = x;
      relNueva[rel.length][1] = y;

      return relNueva;
  }

    /*
     * Comprovau si la relació `rel` és un ordre total sobre `a`. Si ho és retornau el nombre
     * d'arestes del seu diagrama de Hasse. Sino, retornau -2.
     *
     * Podeu soposar que `a` i `rel` estan ordenats de menor a major (`rel` lexicogràficament).
     */
    static int exercici3(int[] a, int[][] rel) {
      System.out.println("<><> EXERCICI 3 <><>");
            if (!ordenTotal(a, rel)) {
                System.out.println("no és un ordre total");
                return -2;
            }

            int numAristas = a.length - 1;

            System.out.println("nombre d'arestes: " + numAristas);
            return numAristas;
    }

    static boolean ordenTotal(int[] a, int[][] rel) {
      //reflexiva
      for (int x : a) {
          boolean esta = false;
          for (int i = 0; i < rel.length; i++) {
              if (rel[i][0] == x && rel[i][1] == x) {
                  esta = true;
              }
          }

          if (!esta) {
              return false;
          }
      }

      //antisimetrica
      for (int i = 0; i < rel.length; i++) {
          int x = rel[i][0];
          int y = rel[i][1];
          // Verificar si (x, y) y (y, x) están en rel
          for (int j = 0; j < rel.length; j++) {
              if (rel[j][0] == y && rel[j][1] == x && x != y) {
                  return false; // Se encontró una pareja no antisimétrica
              }
          }
      }

      //transitiva
      for (int i = 0; i < rel.length; i++) {
          int x = rel[i][0];
          int y = rel[i][1];
          for (int j = 0; j < rel.length; j++) {
              boolean esta = false;
              if (rel[j][0] == y) {
                  int z = rel[j][1];
                  for (int k = 0; k < rel.length; k++) {
                      if (rel[k][0] == x && rel[k][1] == z) {
                          esta = true;
                      }
                  }

                  if (!esta) {
                      rel = insertar(rel, x, z);
                  }
              }
          }
      }

      return true;
  }


    /*
     * Comprovau si les relacions `rel1` i `rel2` són els grafs de funcions amb domini i codomini
     * `a`. Si ho son, retornau el graf de la composició `rel2 ∘ rel1`. Sino, retornau null.
     *
     * Podeu soposar que `a`, `rel1` i `rel2` estan ordenats de menor a major (les relacions,
     * lexicogràficament).
     */
    static int[][] exercici4(int[] a, int[][] rel1, int[][] rel2) {
      System.out.println("<><> EXERCICI 4 <><>");

            for (int i = 0; i < rel1.length; i++) {
                if (!contiene(a, a.length, rel1[i][0]) || !contiene(a, a.length, rel1[i][1])) {
                    System.out.println("el graf no és funció amb domini i codomini a");
                    return null;
                }
            }

            for (int i = 0; i < rel2.length; i++) {
                if (!contiene(a, a.length, rel2[i][0]) || !contiene(a, a.length, rel2[i][1])) {
                    System.out.println("el graf no és funció amb domini i codomini a");
                    return null;
                }
            }

            for (int i = 0; i < a.length; i++) {
                boolean esta = false;
                for (int j = 0; j < rel1.length; j++) {
                    if (rel1[j][0] == a[i]) {
                        esta = true;
                        break;
                    }
                }

                if (!esta) {
                    System.out.println("el graf no és funció amb domini i codomini a");
                    return null;
                }
            }

            for (int i = 0; i < a.length; i++) {
                boolean esta = false;
                for (int j = 0; j < rel2.length; j++) {
                    if (rel2[j][0] == a[i]) {
                        esta = true;
                        break;
                    }
                }

                if (!esta) {
                    System.out.println("el graf no és funció amb domini i codomini a");
                    return null;
                }
            }

            int[][] grafoCompuesto = new int[a.length][2];
            int index = 0;

            for (int i = 0; i < rel1.length; i++) {
                int x = rel1[i][0];
                int y = rel1[i][1];

                for (int j = 0; j < rel2.length; j++) {
                    if (rel2[j][0] == y) {
                        int z = rel2[j][1];
                        grafoCompuesto[index][0] = x;
                        grafoCompuesto[index][1] = z;
                        index++;
                        break;
                    }
                }
            }

            System.out.print("{");
            for (int i = 0; i < grafoCompuesto.length; i++) {
                if (i != grafoCompuesto.length) {
                    System.out.print("{");
                }
                for (int j = 0; j < 2; j++) {
                    System.out.print(grafoCompuesto[i][j]);
                    if (j != 2 - 1) {
                        System.out.print(",");
                    }
                }
                System.out.print("}");
                if (i != grafoCompuesto.length - 1) {
                    System.out.print(",");
                }
            }
            System.out.println("}");

            return grafoCompuesto;
    }

    /*
     * Comprovau si la funció `f` amb domini `dom` i codomini `codom` té inversa. Si la té, retornau
     * el seu graf (el de l'inversa). Sino, retornau null.
     */
    static int[][] exercici5(int[] dom, int[] codom, Function<Integer, Integer> f) {
      System.out.println("<><> EXERCICI 5 <><>");

            int[] resultados = new int[dom.length];
            for (int i = 0; i < dom.length; i++) {
                resultados[i] = f.apply(dom[i]);
            }

            //miar que cada resultado está en el codominio
            for (int y : resultados) {
                if (!contiene(codom, codom.length, y)) {
                    System.out.println("no té inversa");
                    return null;
                }
            }

            //mirar que cada codominio está en el resultado
            for (int y : codom) {
                if (!contiene(resultados, resultados.length, y)) {
                    System.out.println("no té inversa");
                    return null;
                }
            }

            int[][] inversa = new int[dom.length][2];

            for (int i = 0; i < resultados.length; i++) {
                int y = resultados[i];
                for (int j = 0; j < dom.length; j++) {
                    if (f.apply(dom[j]) == y) {
                        inversa[i][0] = y;
                        inversa[i][1] = dom[j];
                        break;
                    }
                }
            }

            System.out.print("{");
            for (int i = 0; i < inversa.length; i++) {
                if (i != inversa.length) {
                    System.out.print("{");
                }
                for (int j = 0; j < 2; j++) {
                    System.out.print(inversa[i][j]);
                    if (j != 2 - 1) {
                        System.out.print(",");
                    }
                }
                System.out.print("}");
                if (i != inversa.length - 1) {
                    System.out.print(",");
                }
            }
            System.out.println("}");

            return inversa;
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // |(a u b) × (a \ c)|

      assertThat(
          exercici1(
            new int[] { 0, 1, 2 },
            new int[] { 1, 2, 3 },
            new int[] { 0, 3 }
          )
          == 8
      );

      assertThat(
          exercici1(
            new int[] { 0, 1 },
            new int[] { 0 },
            new int[] { 0 }
          )
          == 2
      );

      // Exercici 2
      // nombre d'elements de la clausura d'equivalència

      final int[] int08 = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };

      assertThat(exercici2(int08, generateRel(int08, (x, y) -> y == x + 1)) == 81);

      final int[] int123 = { 1, 2, 3 };

      assertThat(exercici2(int123, new int[][] { {1, 3} }) == 5);

      // Exercici 3
      // Si rel és un ordre total, retornar les arestes del diagrama de Hasse

      final int[] int05 = { 0, 1, 2, 3, 4, 5 };

      assertThat(exercici3(int05, generateRel(int05, (x, y) -> x >= y)) == 5);
      assertThat(exercici3(int08, generateRel(int05, (x, y) -> x <= y)) == -2);

      // Exercici 4
      // Composició de grafs de funcions (null si no ho son)

      assertThat(
          exercici4(
            int05,
            generateRel(int05, (x, y) -> x*x == y),
            generateRel(int05, (x, y) -> x == y)
          )
          == null
      );


      var ex4test2 = exercici4(
          int05,
          generateRel(int05, (x, y) -> x + y == 5),
          generateRel(int05, (x, y) -> y == (x + 1) % 6)
        );

      assertThat(
          Arrays.deepEquals(
            lexSorted(ex4test2),
            generateRel(int05, (x, y) -> y == (5 - x + 1) % 6)
          )
      );

      // Exercici 5
      // trobar l'inversa (null si no existeix)
      
      assertThat(exercici5(int05, int08, x -> x + 3) == null);

      assertThat(
          Arrays.deepEquals(
            lexSorted(exercici5(int08, int08, x -> 8 - x)),
            generateRel(int08, (x, y) -> y == 8 - x)
          )
      );
    }

    /**
     * Ordena lexicogràficament un array de 2 dimensions
     * Per exemple:
     *  arr = {{1,0}, {2,2}, {0,1}}
     *  resultat = {{0,1}, {1,0}, {2,2}}
     */
    static int[][] lexSorted(int[][] arr) {
      if (arr == null)
        return null;

      var arr2 = Arrays.copyOf(arr, arr.length);
      Arrays.sort(arr2, Arrays::compare);
      return arr2;
    }

    /**
     * Genera un array int[][] amb els elements {a, b} (a de as, b de bs) que satisfàn pred.test(a, b)
     * Per exemple:
     *   as = {0, 1}
     *   bs = {0, 1, 2}
     *   pred = (a, b) -> a == b
     *   resultat = {{0,0}, {1,1}}
     */
    static int[][] generateRel(int[] as, int[] bs, BiPredicate<Integer, Integer> pred) {
      var rel = new ArrayList<int[]>();

      for (int a : as) {
        for (int b : bs) {
          if (pred.test(a, b)) {
            rel.add(new int[] { a, b });
          }
        }
      }

      return rel.toArray(new int[][] {});
    }

    /// Especialització de generateRel per a = b
    static int[][] generateRel(int[] as, BiPredicate<Integer, Integer> pred) {
      return generateRel(as, as, pred);
    }
  }

  /*
   * Aquí teniu els exercicis del Tema 3 (Grafs).
   *
   * Els (di)grafs vendran donats com llistes d'adjacència (és a dir, tractau-los com diccionaris
   * d'adjacència on l'índex és la clau i els vèrtexos estan numerats de 0 a n-1). Per exemple,
   * podem donar el graf cicle d'ordre 3 com:
   *
   * int[][] c3dict = {
   *   {1, 2}, // veïns de 0
   *   {0, 2}, // veïns de 1
   *   {0, 1}  // veïns de 2
   * };
   *
   * **NOTA: Els exercicis d'aquest tema conten doble**
   */
  static class Tema3 {
    /*
     * Determinau si el graf és connex. Podeu suposar que `g` no és dirigit.
     */
    static boolean exercici1(int[][] g) {
      System.out.println("<><> EXERCICI 1 <><>");

            //si el grafo es vacío o tiene solo un vértice, consideramos que es conexo
            if (g.length <= 1) {
                System.out.println("true");
                return true;
            }

            int n = g.length; //número de vértices
            boolean[] visitado = new boolean[n]; // array para marcar vértices visitados
            int componentesConexas = 0; // contador de componentes conexas

            //realizar la búsqueda en amplitud (BFS) desde cada vértice no visitado
            for (int i = 0; i < n; i++) {
                if (!visitado[i]) {
                    componentesConexas++;
                    bfs(g, i, visitado);
                }
            }

            //si se han visitado todos los vértices, el grafo es conexo
            System.out.println(componentesConexas == 1 && todosVisitados(visitado));
            return componentesConexas == 1 && todosVisitados(visitado);
    }

    static void bfs(int[][] g, int inicio, boolean[] visitado) {
      int n = g.length;
      boolean[] enCola = new boolean[n]; //array para marcar vértices en la cola
      int[] cola = new int[n]; //cola para BFS
      int front = 0, rear = 0; //índices para inicio y final de la cola

      cola[rear++] = inicio;
      visitado[inicio] = true;
      enCola[inicio] = true;

      while (front != rear) {
          int actual = cola[front++];
          enCola[actual] = false;

          // Explorar vecinos del vértice actual
          for (int vecino : g[actual]) {
              if (!visitado[vecino]) {
                  visitado[vecino] = true;
                  if (!enCola[vecino]) {
                      cola[rear++] = vecino;
                      enCola[vecino] = true;
                  }
              }
          }
      }
  }

  static boolean todosVisitados(boolean[] visitado) {
      for (boolean v : visitado) {
          if (!v) {
              return false;
          }
      }
      return true;
  }

    /*
     * Donat un tauler d'escacs d'amplada `w` i alçada `h`, determinau quin és el mínim nombre de
     * moviments necessaris per moure un cavall de la casella `i` a la casella `j`.
     *
     * Les caselles estan numerades de `0` a `w*h-1` per files. Per exemple, si w=5 i h=2, el tauler
     * és:
     *   0 1 2 3 4
     *   5 6 7 8 9
     *
     * Retornau el nombre mínim de moviments, o -1 si no és possible arribar-hi.
     */
    static int exercici2(int w, int h, int i, int j) {
      System.out.println("<><> EXERCICI 2 <><>");

            int[][] moves = {{-2, -1}, {-2, 1}, {-1, 2}, {1, 2}, {2, 1}, {2, -1}, {-1, -2}, {1, -2}};

            //representación del tablero como una matriz
            int board[][] = new int[w][h];

            //inicializamos el tablero con valores -1, que indican que no hemos visitado aún esa casilla
            for (int[] row : board) {
                Arrays.fill(row, -1);
            }

            //definimos las coordenadas de inicio y fin
            int startX = i % w;
            int startY = i / w;
            int targetX = j % w;
            int targetY = j / w;

            //inicializamos la posición de inicio con 0, ya que no hay movimientos necesarios para llegar a ella
            board[startX][startY] = 0;

            //implementación del algoritmo de Dijkstra
            for (int k = 0; k < w * h; k++) {
                for (int x = 0; x < w; x++) {
                    for (int y = 0; y < h; y++) {
                        if (board[x][y] != -1) {
                            for (int[] move : moves) {
                                int newX = x + move[0];
                                int newY = y + move[1];
                                if (newX >= 0 && newX < w && newY >= 0 && newY < h && (board[newX][newY] == -1 || board[newX][newY] > board[x][y] + 1)) {
                                    board[newX][newY] = board[x][y] + 1;
                                }
                            }
                        }
                    }
                }
            }

            //retornamos el valor de la casilla destino, que contiene el número mínimo de movimientos necesarios
            System.out.println("mínim nombre de moviments: " + board[targetX][targetY]);
            return board[targetX][targetY];
    }

    /*
     * Donat un arbre arrelat (graf dirigit `g`, amb arrel `r`), decidiu si el vèrtex `u` apareix
     * abans (o igual) que el vèrtex `v` al recorregut en preordre de l'arbre.
     */
    static boolean exercici3(int[][] g, int r, int u, int v) {
      System.out.println("<><> EXERCICI 3 <><>");

            boolean[] visited = new boolean[g.length];
            boolean[] result = {false}; // Variable para almacenar el resultado de la búsqueda
            preorderDFS(g, r, u, v, visited, result); // Llamada al método modificado
            System.out.println(result[0]);
            return result[0];
    }

    static void preorderDFS(int[][] g, int current, int u, int v, boolean[] visited, boolean[] result) {
      visited[current] = true;

      // Si ya se encontraron tanto u como v, no hay necesidad de seguir buscando
      if (result[0]) {
          return;
      }

      if (current == u) {
          result[0] = true; // Marcamos que u ha sido encontrado
      }
      if (current == v && !result[0]) {
          // Si encontramos v antes de encontrar u, el resultado debe ser false
          result[0] = false;
          return;
      }
      for (int neighbor : g[current]) {
          if (!visited[neighbor]) {
              preorderDFS(g, neighbor, u, v, visited, result);
          }
      }
  }

    /*
     * Donat un recorregut en preordre (per exemple, el primer vèrtex que hi apareix és `preord[0]`)
     * i el grau de cada vèrtex (per exemple, el vèrtex `i` té grau `d[i]`), trobau l'altura de
     * l'arbre corresponent.
     *
     * L'altura d'un arbre arrelat és la major distància de l'arrel a les fulles.
     */
    static int exercici4(int[] preord, int[] d) {
      System.out.println("<><> EXERCICI 4 <><>");

            int maxHeight = 0;
            int currentHeight = 0;
            int nodeIndex = 0;
            int[] nodeChildren = new int[preord.length];
            int currentIndex = 0;

            // Recorrer el preorden
            while (nodeIndex < preord.length) {
                maxHeight = Math.max(maxHeight, currentHeight);
                int children = d[preord[nodeIndex]];

                if (children > 0) {
                    // Empieza un nuevo nivel
                    nodeChildren[currentIndex++] = children;
                    currentHeight++;
                } else {
                    // Retrocede hasta encontrar un nodo con hijos no procesados
                    while (currentIndex > 0 && nodeChildren[currentIndex - 1] == 1) {
                        currentIndex--;
                        currentHeight--;
                    }

                    if (currentIndex > 0) {
                        nodeChildren[currentIndex - 1]--;
                    }
                }
                nodeIndex++;
            }

            System.out.println("altura arbre: " + maxHeight);
            return maxHeight;
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // G connex?

      final int[][] B2 = { {}, {} };

      final int[][] C3 = { {1, 2}, {0, 2}, {0, 1} };

      final int[][] C3D = { {1}, {2}, {0} };

      assertThat(exercici1(C3));
      assertThat(!exercici1(B2));

      // Exercici 2
      // Moviments de cavall

      // Tauler 4x3. Moviments de 0 a 11: 3.
      // 0  1   2   3
      // 4  5   6   7
      // 8  9  10  11
      assertThat(exercici2(4, 3, 0, 11) == 3);

      // Tauler 3x2. Moviments de 0 a 2: (impossible).
      // 0 1 2
      // 3 4 5
      assertThat(exercici2(3, 2, 0, 2) == -1);

      // Exercici 3
      // u apareix abans que v al recorregut en preordre (o u=v)

      final int[][] T1 = {
        {1, 2, 3, 4},
        {5},
        {6, 7, 8},
        {},
        {9},
        {},
        {},
        {},
        {},
        {10, 11},
        {},
        {}
      };

      assertThat(exercici3(T1, 0, 5, 3));
      assertThat(!exercici3(T1, 0, 6, 2));

      // Exercici 4
      // Altura de l'arbre donat el recorregut en preordre

      final int[] P1 = { 0, 1, 5, 2, 6, 7, 8, 3, 4, 9, 10, 11 };
      final int[] D1 = { 4, 1, 3, 0, 1, 0, 0, 0, 0, 2,  0,  0 };

      final int[] P2 = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
      final int[] D2 = { 2, 0, 2, 0, 2, 0, 2, 0, 0 };

      assertThat(exercici4(P1, D1) == 3);
      assertThat(exercici4(P2, D2) == 4);
    }
  }

  /*
   * Aquí teniu els exercicis del Tema 4 (Aritmètica).
   *
   * En aquest tema no podeu:
   *  - Utilitzar la força bruta per resoldre equacions: és a dir, provar tots els nombres de 0 a n
   *    fins trobar el que funcioni.
   *  - Utilitzar long, float ni double.
   *
   * Si implementau algun dels exercicis així, tendreu un 0 d'aquell exercici.
   */
  static class Tema4 {
    /*
     * Calculau el mínim comú múltiple de `a` i `b`.
     */
    static int exercici1(int a, int b) {
      System.out.println("<><> EXERCICI 1 <><>");

            if (a == 0 || b == 0) {
                System.out.println("mcm: 0");
                return 0; // Si alguno es 0, el mcm es 0
            }
            int mcd = euclides(a, b);
            System.out.println("mcm: " + Math.abs(a * b) / mcd);
            return Math.abs(a * b) / mcd;
    }

    static int euclides(int a, int b) {
      while (b != 0) {
          int temp = b;
          b = a % b;
          a = temp;
      }
      return a;
  }

    /*
     * Trobau totes les solucions de l'equació
     *
     *   a·x ≡ b (mod n)
     *
     * donant els seus representants entre 0 i n-1.
     *
     * Podeu suposar que `n > 1`. Recordau que no no podeu utilitzar la força bruta.
     */
    static int[] exercici2(int a, int b, int n) {
      System.out.println("<><> EXERCICI 2 <><>");

            int d = euclides(a, n);

            // Si gcd(a, n) no divide a b, no hay soluciones
            if (b % d != 0) {
                System.out.println("no existeixen");
                return new int[]{};
            }

            // Simplificar la ecuación dividiendo por d
            a /= d;
            b /= d;
            n /= d;

            // Encontrar una solución particular usando el inverso modular
            int a_inv = modInverse(a, n);
            int x0 = (a_inv * b) % n;

            // Generar todas las soluciones
            int[] solutions = new int[d];
            for (int i = 0; i < d; i++) {
                solutions[i] = (x0 + i * n) % (n * d);
            }

            System.out.print("{");
            for (int i = 0; i < solutions.length; i++) {
                System.out.print(solutions[i]);
                if (i < solutions.length - 1) {
                    System.out.print(",");
                }
            }
            System.out.println("}");

            return solutions;
    }

    static int[] extendedGCD(int a, int b) {
      if (b == 0) {
          return new int[]{a, 1, 0};
      }
      int[] result = extendedGCD(b, a % b);
      int gcd = result[0];
      int x1 = result[1];
      int y1 = result[2];
      int x = y1;
      int y = x1 - (a / b) * y1;
      return new int[]{gcd, x, y};
  }

  static int modInverse(int a, int n) {
      int[] result = extendedGCD(a, n);
      int gcd = result[0];
      int x = result[1];
      // Asegurarse de que el inverso es positivo
      if (gcd != 1) {
          return -1;
      }
      return (x % n + n) % n;
  }

    /*
     * Donats `a != 0`, `b != 0`, `c`, `d`, `m > 1`, `n > 1`, determinau si el sistema
     *
     *   a·x ≡ c (mod m)
     *   b·x ≡ d (mod n)
     *
     * té solució.
     */
    static boolean exercici3(int a, int b, int c, int d, int m, int n) {
      System.out.println("<><> EXERCICI 3 <><>");

            if (c % euclides(a, m) == 0 && d % euclides(b, n) == 0
                    && (d / euclides(b, n) - c / euclides(a, m)) % euclides(m / euclides(a, m), n / euclides(b, n)) == 0) {
                System.out.println("true");
                return true;
            }

            System.out.println("false");
            return false;
    }

    /*
     * Donats `n` un enter, `k > 0` enter, i `p` un nombre primer, retornau el residu de dividir n^k
     * entre p.
     *
     * Alerta perquè és possible que n^k sigui massa gran com per calcular-lo directament.
     * De fet, assegurau-vos de no utilitzar cap valor superior a max{n, p²}.
     *
     * Anau alerta també abusant de la força bruta, la vostra implementació hauria d'executar-se en
     * qüestió de segons independentment de l'entrada.
     */
    static int exercici4(int n, int k, int p) {
      System.out.println("<><> EXERCICI 4 <><>");

            // Aseguramos que n sea no negativo
            while (n < 0) {
                n += p;
            }

            // Calculamos phi(p)
            int phiP = p - 1;

            // Reducimos k módulo phi(p)
            k %= phiP;

            // Calculamos n^k % p
            int result = power(n, k, p);

            System.out.println(result);
            return result;
    }

    static int power(int a, int b, int m) {
      int result = 1; // Inicializamos el resultado como 1

      // Hacemos exponenciación modular
      a = a % m;
      while (b > 0) {
          // Si b es impar, multiplicamos el resultado por a
          if (b % 2 == 1) {
              result = (result * a) % m;
          }
          // Reducimos b a la mitad y a al cuadrado
          b /= 2;
          a = (a * a) % m;
      }

      return result;
  }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // mcm(a, b)

      assertThat(exercici1(35, 77) == 5*7*11);
      assertThat(exercici1(-8, 12) == 24);

      // Exercici 2
      // Solucions de a·x ≡ b (mod n)

      assertThat(Arrays.equals(exercici2(2, 2, 4), new int[] { 1, 3 }));
      assertThat(Arrays.equals(exercici2(3, 2, 4), new int[] { 2 }));

      // Exercici 3
      // El sistema a·x ≡ c (mod m), b·x ≡ d (mod n) té solució?

      assertThat(exercici3(3, 2, 2, 2, 5, 4));
      assertThat(!exercici3(3, 2, 2, 2, 10, 4));

      // Exercici 4
      // n^k mod p

      assertThat(exercici4(2018, 2018, 5) == 4);
      assertThat(exercici4(-2147483646, 2147483645, 46337) == 7435);
    }
  }

  /**
   * Aquest mètode `main` conté alguns exemples de paràmetres i dels resultats que haurien de donar
   * els exercicis. Podeu utilitzar-los de guia i també en podeu afegir d'altres (no els tendrem en
   * compte, però és molt recomanable).
   *
   * Podeu aprofitar el mètode `assertThat` per comprovar fàcilment que un valor sigui `true`.
   */
  public static void main(String[] args) {
    Tema1.tests();
    Tema2.tests();
    Tema3.tests();
    Tema4.tests();
  }

  /// Si b és cert, no fa res. Si b és fals, llança una excepció (AssertionError).
  static void assertThat(boolean b) {
    if (!b)
      throw new AssertionError();
  }
}

// vim: set textwidth=100 shiftwidth=2 expandtab :
