package edu.wit.cs.comp3370;

// TODO: copy your lab 4 BST code here
public class BST extends LocationHolder {
	public BST() {
		root = nil;
	}

	@Override
	public DiskLocation next(DiskLocation d) {
		if (!d.right.equals(nil)) {
			return d.right;
		} else {
			return up(d);
		}
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
			return d.left;
		} else {
			return down(d);
		}
	}

	private DiskLocation down(DiskLocation x) {
		DiskLocation p = x.parent;
		if (p.equals(nil) || x == p.right)
			return p;
		else
			return down(p);
	}

	@Override
	public void insert(DiskLocation d) {

		d.right = d.left = d.parent = nil;
		// DiskLocation parent = findParent(d, root, nil);
		d.parent = findParent(d, root, nil);
		if (d.parent.equals(nil)) {
			root = d;
			return;
		}
		if (d.isLessThan(d.parent))
			d.parent.left = d;
		else
			d.parent.right = d;
	}

	private DiskLocation findParent(DiskLocation n, DiskLocation curr, DiskLocation parent) {
		if (curr.equals(nil))
			return parent;
		if (n.isLessThan(curr))
			return findParent(n, curr.left, curr);
		else
			return findParent(n, curr.right, curr);
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

}
