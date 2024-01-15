package com.lerokko.betterplace;

import org.bukkit.Axis;
import org.bukkit.block.BlockFace;
import org.bukkit.util.Vector;

public class ClickInfo {

    private final BlockFace direction;
    private final Axis closestAxis;

    public ClickInfo(Vector vector) {
        this.direction = getClosestCardinalDirection(vector);
        this.closestAxis = getClosestAxis(vector);
    }

    public BlockFace getDirection() {
        return direction;
    }

    public Axis getAxis() {
        return closestAxis;
    }

    private BlockFace getClosestCardinalDirection(Vector vector) {
        double x = vector.getX();
        double y = vector.getY();
        double z = vector.getZ();

        // Determine the closest cardinal direction based on the vector components
        if (Math.abs(x) > Math.abs(y) && Math.abs(x) > Math.abs(z)) {
            return (x > 0) ? BlockFace.EAST : BlockFace.WEST;
        } else if (Math.abs(y) > Math.abs(x) && Math.abs(y) > Math.abs(z)) {
            return (y > 0) ? BlockFace.UP : BlockFace.DOWN;
        } else {
            return (z > 0) ? BlockFace.SOUTH : BlockFace.NORTH;
        }
    }

    private Axis getClosestAxis(Vector vector) {
        double x = Math.abs(vector.getX());
        double y = Math.abs(vector.getY());
        double z = Math.abs(vector.getZ());

        // Determine the closest axis based on the vector components
        if (x > y && x > z) {
            return Axis.X;
        } else if (y > x && y > z) {
            return Axis.Y;
        } else {
            return Axis.Z;
        }
    }

}