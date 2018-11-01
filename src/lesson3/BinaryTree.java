package lesson3;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
@SuppressWarnings("WeakerAccess")
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {

    private static class Node<T> {
        final T value;

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;

    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        }
        else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        }
        else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    public boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    /**
     * Удаление элемента в дереве
     * Средняя
     */

    //n-высота дерева
    // Трудоемкость O(n)
    // Ресурсоемкость O(n)
    @Override
    public boolean remove(Object o) {
        if (root==null) return false;
        root=removeHelper(root, (T) o);
        size--;
        return true;
    }

    private Node<T> removeHelper(Node<T> node,T o1) {
        if (node==null) return null;

        int compare = o1.compareTo(node.value);

        if (compare>0) {
            node.right = removeHelper(node.right, o1);
        } else if (compare < 0) {
            node.left = removeHelper(node.left, o1);
        } else {
                if (node.right == null || node.left == null) {

                    if (node.right == null) return node.left;
                    else return node.right;

                } else {

                    Node<T> nodeMinimum = node.right;
                    while (nodeMinimum.left != null) {          //поиск минимального элемента
                        nodeMinimum = nodeMinimum.left;
                    }

                    Node minimum = new Node<>(nodeMinimum.value);
                    minimum.left = node.left;
                    minimum.right = node.right;
                    node = minimum;

                    node.right = removeHelper(node.right, node.value);
                }
            }
        return node;
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        }
        else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        }
        else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {

        private Node<T> current = null;

        private BinaryTreeIterator() {}

        /**
         * Поиск следующего элемента
         * Средняя
         */

        //n-высота дерева
        // Трудоемкость O(n)
        // Ресурсоемкость O(n)
        private Node<T> findNext() {
            Node<T> nodeNext = null;

            if (current != null) {

                Node<T> root1 = root;
                while (root1 != null) {

                    int compare = root1.value.compareTo(current.value);
                    if (compare > 0) {
                        nodeNext = root1;
                        root1 = root1.left;
                    } else {
                        root1 = root1.right;
                    }

                }
            } else {

                nodeNext = root;
                while (nodeNext.left != null) {
                    nodeNext = nodeNext.left;
                }

            }
            return nodeNext;
        }

        @Override
        public boolean hasNext() {
            return findNext() != null;
        }

        @Override
        public T next() {
            current = findNext();
            if (current == null) throw new NoSuchElementException();
            return current.value;
        }

        /**
         * Удаление следующего элемента
         * Сложная
         */
        @Override
        public void remove() {
            root = removeHelper(root, current.value);
            size--;
        }
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    /**
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
     * Очень сложная
     */
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */

    // Трудоемкость O(n)
    // Ресурсоемкость O(n)
    public T element;
    public SortedSet<T> sortedSet = new TreeSet<>();

    private void passTroughTree(Node<T> node,boolean numberTask) {
        int compare = element.compareTo(node.value);
        if (numberTask) {
            if (compare > 0) {
                sortedSet.add(node.value);
                if (node.left != null) addInfinity(node.left);
                if (node.right != null) passTroughTree(node.right,true);
            } else {
                if (node.left!=null) {
                    if (compare < 0)
                        passTroughTree(node.left,true);
                    else addInfinity(node.left);
                }
            }
        } else {
            if (compare>0){
                if (node.right!=null) passTroughTree(node.right,false);
            } else {
                sortedSet.add(node.value);
                if (node.right != null) addInfinity(node.right);
                if (node.left != null) passTroughTree(node.left,false);
            }

        }
    }

    private void addInfinity(Node<T> node) {
        sortedSet.add(node.value);
        if (node.right!=null) addInfinity(node.right);
        if (node.left!=null) addInfinity(node.left);
    }

    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        element=toElement;
        if (root!=null) passTroughTree(root,true);
        return sortedSet;
    }

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */

    // Трудоемкость O(n)
    // Ресурсоемкость O(n)
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
            element=fromElement;
            if (root!=null) passTroughTree(root,false);
            return sortedSet;
        }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}
