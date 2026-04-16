using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class LevelGenerator : MonoBehaviour
{
    public GameObject obstaclePrefab; // Engel prefabı
    public float obstacleSpawnInterval = 2f; // Yeni engel spawn aralığı

    private void Start()
    {
        // Yeni engelleri belirli aralıklarla oluşturmak için bir Coroutine başlat
        StartCoroutine(SpawnObstacles());
    }

    private IEnumerator SpawnObstacles()
    {
        while (true)
        {
            // Engel prefabından yeni bir engel oluştur
            GameObject newObstacle = Instantiate(obstaclePrefab, transform.position, Quaternion.identity);

            // Oluşturulan engelin sola doğru hareketini yöneten bir script ekleyin
            // ObstacleMovement obstacleMovement = newObstacle.GetComponent<ObstacleMovement>();
            // if (obstacleMovement != null)
            // {
            //     obstacleMovement.Initialize();
            // }

            // Belirli aralıklarla yeni engel oluştur
            yield return new WaitForSeconds(obstacleSpawnInterval);
        }
    }
}
