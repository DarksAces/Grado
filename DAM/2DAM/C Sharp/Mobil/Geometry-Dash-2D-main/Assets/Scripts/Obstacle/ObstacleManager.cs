using System.Collections;
using UnityEngine;

public class ObstacleManager : MonoBehaviour
{
    public GameObject obstaclePrefab; // Engel prefabı
    public Transform spawnPoint; // Engel spawn noktası
    public float spawnInterval = 1f; // Engel spawn aralığı (saniye)
    public float obstacleSpeed = 5f; // Engel hareket hızı
    public int maxSpawnCount = 999999;

    public GameObject Portal;

    private void Start()
    {
        StartCoroutine(SpawnObstacles());
    }

   
    private IEnumerator SpawnObstacles()
    {
        int obstacleCount = 0;

        while (true) // Bucle infinito para que el juego no se acabe
        {
            int pattern = Random.Range(0, 4); // 0: Pincho único, 1: Triple pincho, 2: Plataforma corta, 3: Plataforma larga

            if (pattern == 0) // Pincho único
            {
                SpawnItem(false, 1f);
                yield return new WaitForSeconds(spawnInterval * 2f);
            }
            else if (pattern == 1) // Triple pincho
            {
                for (int i = 0; i < 3; i++)
                {
                    SpawnItem(false, 1f);
                    yield return new WaitForSeconds(0.7f); // Más espacio
                }
                yield return new WaitForSeconds(spawnInterval * 2f);
            }
            else if (pattern == 2) // Plataforma corta (salto entre ellas)
            {
                SpawnItem(true, 3.5f);
                yield return new WaitForSeconds(spawnInterval * 2f);
            }
            else // Plataforma larga
            {
                SpawnItem(true, 7f);
                yield return new WaitForSeconds(spawnInterval * 2.5f);
            }

            obstacleCount++;
        }
    }

    private void SpawnItem(bool isPlatform, float width)
    {
        // Usamos posiciones GLOBALES para evitar que el spawnPoint mal colocado las oculte
        float yPos = isPlatform ? 0.0f : -2.0f; 
        Vector3 spawnPosition = new Vector3(spawnPoint.position.x, yPos, 0);
        GameObject item = Instantiate(obstaclePrefab, spawnPosition, Quaternion.identity);
        item.transform.localScale = new Vector3(width, isPlatform ? 0.8f : 0.5f, 1f); // Plataformas mas gruesas
        
        SpriteRenderer sr = item.GetComponent<SpriteRenderer>();
        Obstacle obstacleScript = item.GetComponent<Obstacle>();
        
        BoxCollider2D bc = item.GetComponent<BoxCollider2D>();
        if (bc == null) bc = item.AddComponent<BoxCollider2D>(); // ¡SI NO TIENE, LO CREAMOS!

        if (isPlatform)
        {
            item.layer = 3; 
            item.tag = "Ground";
            if (sr != null) sr.color = Color.cyan;
            if (obstacleScript != null) obstacleScript.isKillZone = false;
            
            bc.isTrigger = false; 
            bc.usedByEffector = false; 
            bc.size = new Vector2(1f, 1f); 
        }
        else
        {
            item.layer = 0; 
            if (sr != null) sr.color = Color.red;
            if (obstacleScript != null) obstacleScript.isKillZone = true;
            
            // Si es un pincho, sustituimos por polígono
            Destroy(bc); 
            PolygonCollider2D poly = item.AddComponent<PolygonCollider2D>();
            Vector2[] points = new Vector2[3];
            points[0] = new Vector2(-0.35f, -0.4f); 
            points[1] = new Vector2(0.35f, -0.4f);  
            points[2] = new Vector2(0f, 0.4f);      
            poly.points = points;
            poly.isTrigger = true;
        }

        StartCoroutine(MoveObstacle(item));
    }


    private IEnumerator MoveObstacle(GameObject obstacle)
{
    if (obstacle == null) yield break;

    Vector3 direction = Vector3.left;
    bool scoreCounted = false;

    while (obstacle != null && obstacle.transform.position.x > -20f)
    {
        obstacle.transform.Translate(direction * obstacleSpeed * Time.deltaTime);
        
        // Sumar puntuación al pasar al jugador (x < 0)
        if (!scoreCounted && obstacle.transform.position.x < -2f) {
            GameManager.Instance.IncreaseScore();
            scoreCounted = true;
        }

        yield return null;
    }

    if (obstacle != null) Destroy(obstacle);
}





    private IEnumerator MovePortal(GameObject Portal)
    {
        Vector3 direction = Vector3.left;

        while (Portal.transform.position.x > -20f)
        {
            Portal.transform.Translate(direction * obstacleSpeed * Time.deltaTime);
            yield return null;
        }

        Destroy(Portal);
    }
}
