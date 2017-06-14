
package edu.wit.cs.comp3370;

/* Implements methods to use a red-black tree 
 * 
 * Wentworth Institute of Technology
 * COMP 3370
 * Lab Assignment 5
 * 
 */

public class RBTree extends LocationHolder {
	public RBTree() {
		nil.left = nil.right = nil.parent = nil;
		root = nil;
	}

	/*
	 * sets a disk location's color to red.
	 * 
	 * Use this method on fix-insert instead of directly coloring nodes red to
	 * avoid setting nil as red.
	 */
	private void setRed(DiskLocation z) {
		if (z != nil)
			z.color = RB.RED;
	}

	@Override
	public DiskLocation next(DiskLocation d) {
		if (!d.right.equals(nil)) {
			return min(d.right);
		} else {
			return up(d);
		}
	}

	private DiskLocation min(DiskLocation x) {
		if (x.left == nil) {
			return x;
		}
		return min(x.left);
	}

	private DiskLocation up(DiskLocation x) {
		DiskLocation p = x.parent;
		if (p.equals(nil) || x == p.left)
			return p;
		else
			return up(p);
	}

	@Override
	public DiskLocation prev(DiskLocation d) {
		if (!d.left.equals(nil)) {
			return max(d.left);
		} else {
			return down(d);
		}
	}

	private DiskLocation max(DiskLocation x) {
		if (x.right == nil) {
			return x;
		}
		return max(x.right);
	}

	private DiskLocation down(DiskLocation x) {
		DiskLocation p = x.parent;
		if (p.equals(nil) || x == p.right)
			return p;
		else
			return down(p);
	}

	@Override
	public int height() {
		return getHeight(root) - 1;
	}

	private int getHeight(DiskLocation d) {
		if (d.equals(nil))
			return 0;
		return 1 + Math.max(getHeight(d.right), getHeight(d.left));
	}

	@Override
	public DiskLocation find(DiskLocation d) {
		return findRecur(root, d);
	}

	private DiskLocation findRecur(DiskLocation curr, DiskLocation target) {
		if (curr.equals(target) || curr.equals(nil)) {
			return curr;
		}
		if (target.isGreaterThan(curr)) {
			return findRecur(curr.right, target);
		} else {
			return findRecur(curr.left, target);
		}
	}

	@Override
	public void insert(DiskLocation z) {
		z.left = z.right = z.parent = nil;

		DiskLocation y = nil, x = root;
		while (x != nil) {
			y = x;
			if (x.isGreaterThan(z)) {
				x = x.left;
			} else {
				x = x.right;
			}
		}
		z.parent = y;
		if (y == nil) {
			root = z;
		} else if (y.isGreaterThan(z)) {
			y.left = z;
		} else {
			y.right = z;
		}
		setRed(z);
		fixInsert(z);
	}

	public void fixInsert(DiskLocation z) {
		while (z.parent.color == RB.RED) {
			if (z.parent == z.parent.parent.left) {
				DiskLocation y = z.parent.parent.right;
				if (y.color == RB.RED) {
					z.parent.color = RB.BLACK;
					y.color = RB.BLACK;
					setRed(z.parent.parent);
					z = z.parent.parent;
				} else {
					if (z == z.parent.right) {
						z = z.parent;
						rotateLeft(z);
					}
					z.parent.color = RB.BLACK;
					setRed(z.parent.parent);
					rotateRight(z.parent.parent);
				}
			} else {
				DiskLocation y = z.parent.parent.left;
				if (y.color == RB.RED) {
					z.parent.color = RB.BLACK;
					y.color = RB.BLACK;
					setRed(z.parent.parent);
					z = z.parent.parent;
				} else {
					if (z == z.parent.left) {
						z = z.parent;
						rotateRight(z);
					}
					z.parent.color = RB.BLACK;
					setRed(z.parent.parent);
					rotateLeft(z.parent.parent);

				}
			}
		}
		root.color = RB.BLACK;
	}

	private void rotateLeft(DiskLocation x) {
		DiskLocation y = x.right;
		x.right = y.left;

		if (y.left != nil) {
			y.left.parent = x;
		}

		y.parent = x.parent;

		if (x.parent == nil) {
			root = y;
		} else if (x == x.parent.left) {
			x.parent.left = y;
		} else
			x.parent.right = y;
		y.left = x;
		x.parent = y;
	}

	private void rotateRight(DiskLocation x) {
		DiskLocation y = x.left;
		x.left = y.right;

		if (y.right != nil) {
			y.right.parent = x;
		}

		y.parent = x.parent;

		if (x.parent == nil) {
			root = y;
		} else if (x == x.parent.right) {
			x.parent.right = y;
		} else
			x.parent.left = y;
		y.right = x;
		x.parent = y;
	}

	@Override
	public String toString() {
		return root.treeString();
	}
}
