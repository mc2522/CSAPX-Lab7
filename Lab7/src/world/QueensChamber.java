package world;

import bee.Drone;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The queen's chamber is where the mating ritual between the queen and her
 * drones is conducted.  The drones will enter the chamber in order.
 * If the queen is ready and a drone is in here, the first drone will
 * be summoned and mate with the queen.  Otherwise the drone has to wait.
 * After a drone mates they perish, which is why there is no routine
 * for exiting (like with the worker bees and the flower field).
 *
 * @author Sean Strout @ RIT CS
 * @author Mike Cao
 */
public class QueensChamber {
    private ConcurrentLinkedQueue<Drone> drones = new ConcurrentLinkedQueue<>();

    /**
     * Makes a drone enter the chamber if there are drones waiting in the queue.
     * @param drone
     * @throws InterruptedException
     */
    public synchronized void enterChamber(Drone drone) throws InterruptedException {
        System.out.println("*QC*" + drone + " enters chamber");
        drones.add(drone);
        if(!drones.peek().equals(drone))
            wait();
        System.out.println("*QC*" + drone + " leaves chamber");
    }

    /**
     * Summons a drone.
     */
    public synchronized void summonDrone() {
        notifyAll();
        Drone drone = drones.poll();
        drone.setMated();
        System.out.println("*QC* Queen mates with " + drone);
    }

    /**
     * Dismisses an individual drone.
     */
    public synchronized void dismissDrone() {
        drones.remove();
        notifyAll();
    }

    /**
     * Checks if the drones size is larger than zero, or if drones has any drones.
     * @return boolean true or false
     */
    public synchronized boolean hasDrone() {
        if(drones.size() > 0)
            return true;
        return false;
    }
}