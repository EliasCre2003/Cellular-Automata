package eliascregard.main;

import eliascregard.cells.*;
import eliascregard.input.*;
import eliascregard.interactives.MaterialSwitch;
import eliascregard.interactives.RadioButtons;
import eliascregard.interactives.Switch;
import eliascregard.physics.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


public class GamePanel extends JPanel implements Runnable {
    private Dimension WINDOW_SIZE = Main.WINDOW_SIZE;
    private final Dimension DEFAULT_WINDOW_SIZE = new Dimension(1000, 1000);
    private double WINDOW_SCALE = (double) WINDOW_SIZE.width / DEFAULT_WINDOW_SIZE.width;
    private int MAX_FRAME_RATE = 200;
    private Thread gameThread;
    int ticks = 0;
    private final GameTime time = new GameTime();
    private final KeyHandler keys = new KeyHandler();
    private final MouseHandler mouse = new MouseHandler(WINDOW_SCALE);
    private boolean lastMouseLeftButtonState = false;
    private boolean lastMouseRightButtonState = false;
    private double deltaTime;
    private int tickSpeed;
    private double renderDeltaT = 0;
    private int fps;
    private double cellSize = 4;
    private CellGrid cellGrid = new CellGrid(
            (int)(DEFAULT_WINDOW_SIZE.width / cellSize),
            (int)(DEFAULT_WINDOW_SIZE.height / cellSize)
    );
    private int currentMouseWheelRotation = mouse.getWheelRotation();
    private int previousMouseWheelRotation = mouse.getWheelRotation();
    private double brushRadius = 0.5;

    private final RadioButtons cellTypeButtons = new RadioButtons();
    private final MaterialSwitch sandSwitch = new MaterialSwitch(CellTypes.SAND, Color.WHITE, Sand.COLOR, "",
            new Vector2D(10, 10), 50, 50);
    private final MaterialSwitch waterSwitch = new MaterialSwitch(CellTypes.WATER, Color.WHITE, Water.COLOR, "",
            new Vector2D(70, 10), 50, 50);
    private final MaterialSwitch ironSwitch = new MaterialSwitch(CellTypes.IRON, Color.WHITE, Iron.COLOR, "",
            new Vector2D(130, 10), 50, 50);
    private final MaterialSwitch woodSwitch = new MaterialSwitch(CellTypes.WOOD, Color.WHITE, Wood.COLOR, "",
            new Vector2D(190, 10), 50, 50);
    private final MaterialSwitch thermiteSwitch = new MaterialSwitch(CellTypes.THERMITE, Color.WHITE, Thermite.COLOR,
            "", new Vector2D(250, 10), 50, 50);
    private final MaterialSwitch lavaSwitch = new MaterialSwitch(CellTypes.LAVA, Color.WHITE, Lava.COLOR, "",
            new Vector2D(310, 10), 50, 50);

    private Class<? extends Cell> selectedMaterial = CellTypes.SAND;
    private Constructor<? extends Cell> selectedMaterialConstructor;

    private void sleep(int nanoseconds) {
        try {
            Thread.sleep((long) GameTime.nanoSecondsToMilliSeconds(nanoseconds), nanoseconds % 1_000_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void updateBrushRadius() {
        currentMouseWheelRotation = mouse.getWheelRotation();
        brushRadius += (currentMouseWheelRotation - previousMouseWheelRotation) * -0.5;
        if (brushRadius < 0.5) {
            brushRadius = 0.5;
        }
        previousMouseWheelRotation = currentMouseWheelRotation;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public GamePanel() {
        setPreferredSize(WINDOW_SIZE);
        setBackground(new Color(0, 0, 0));
        setDoubleBuffered(true);
        addKeyListener(keys);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        addMouseWheelListener(mouse);
        setFocusable(true);
    }


    @Override
    public void run() {

        try {
            selectedMaterialConstructor = selectedMaterial.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        cellTypeButtons.addButtons(
                new Switch[] {
                        sandSwitch,
                        waterSwitch,
                        ironSwitch,
                        woodSwitch,
                        thermiteSwitch,
                        lavaSwitch
                }
        );
        cellTypeButtons.setSelectedButton(sandSwitch);

        while (gameThread != null) {
            deltaTime = time.getDeltaTime();
            if (ticks == 0) {
                deltaTime = 0;}
            tickSpeed = time.getFPS(deltaTime);
            renderDeltaT += deltaTime;
            if (MAX_FRAME_RATE == 0) {
                fps = time.getFPS(deltaTime);
            }

            if (keys.escapePressed) {
                System.exit(0);
            }
//            sleep(100_000_000);
            try {
                update();
            } catch (InvocationTargetException | IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
            ticks++;
            if (MAX_FRAME_RATE > 0) {
                if (renderDeltaT >= 1.0 / MAX_FRAME_RATE) {
                    fps = time.getFPS(renderDeltaT);
                    repaint();
                    renderDeltaT -= 1.0 / MAX_FRAME_RATE;
                }
            } else {
                repaint();
            }
        }
    }

    public void update() throws InvocationTargetException, InstantiationException, IllegalAccessException {

        updateBrushRadius();
        Switch selectedSwitch = cellTypeButtons.getSelectedButton();
        selectedMaterial = ((MaterialSwitch) selectedSwitch).getMaterial();

        try {
            selectedMaterialConstructor = selectedMaterial.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        if (keys.rPressed) {
            cellGrid.reset();
        }

        int x = (int) (mouse.getX() / cellSize);
        int y = (int) (mouse.getY() / cellSize);
        if (mouse.leftIsPressed()) {
            if (lastMouseLeftButtonState) {
                for (int i = (int) (x - brushRadius); i < x + brushRadius; i++) {
                    for (int j = (int) (y - brushRadius); j < y + brushRadius; j++) {
                        if (Math.sqrt(Math.pow(i - x, 2) + Math.pow(j - y, 2)) < brushRadius) {
                            cellGrid.setCell(i, j, selectedMaterialConstructor.newInstance());
                        }
                    }
                }
            }
            else {
                cellTypeButtons.update(mouse);
                lastMouseLeftButtonState = true;
            }
        }
        else {
            lastMouseLeftButtonState = false;
        }
        if (mouse.rightIsPressed()) {
            cellGrid.fillCircle(x, y, brushRadius, new Air());
            lastMouseRightButtonState = true;
        }
        else {
            lastMouseRightButtonState = false;
        }


        cellGrid.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(new Color(0, 0, 0));
        g2.fillRect(0, 0, WINDOW_SIZE.width, WINDOW_SIZE.height);

        cellGrid.render(g2, cellSize * WINDOW_SCALE);
        cellTypeButtons.render(g2, WINDOW_SCALE);

        g2.setColor(Color.GREEN);
        g2.drawOval(
                (int) ((mouse.getX() - brushRadius * cellSize) * WINDOW_SCALE),
                (int) ((mouse.getY() - brushRadius * cellSize) * WINDOW_SCALE),
                (int) (brushRadius * 2 * cellSize * WINDOW_SCALE),
                (int) (brushRadius * 2 * cellSize * WINDOW_SCALE)
        );

        g2.dispose();
    }
}